package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.*;
import com.fpmislata.basespring.persistence.dao.db.GenericDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class BaseDaoJdbc<T> implements GenericDaoDb<T> {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GenericRowMapper<T> rowMapper;
    protected final Class<T> entityClass;

    public BaseDaoJdbc(Class<T> entityClass, DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.entityClass = entityClass;
        this.rowMapper = new GenericRowMapper<>(entityClass, jdbcTemplate);
    }

    public T customSqlQuery(String sql, Map<String, ?> params) {
        try {
            if (params != null && !params.isEmpty()) {
                return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
            } else {
                return jdbcTemplate.queryForObject(sql, rowMapper);
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<T> customSqlQueryForList(String sql, Map<String, ?> params) {
        if (params != null && !params.isEmpty()) {
            return namedParameterJdbcTemplate.query(sql, params, rowMapper);
        } else {
            return jdbcTemplate.query(sql, rowMapper);
        }
    }

    public List<T> getAll() {
        String sql = getSelectSql();
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<T> getAll(int page, int size) {
        String sql = getSelectSql() + " LIMIT ? OFFSET ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql, rowMapper, size, (page - 1) * size);
    }

    public List<T> getAllByIds(Long[] ids) {
        String sql = getSelectSql() + " WHERE id IN (:ids)";
        Map<String, List<Long>> params = Map.of("ids", Arrays.asList(ids));
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    public List<T> getByCriteria(Map<String, Object> criteria) {
        String sql = buildSqlWithCriteria(getSelectSql(), criteria);
        return namedParameterJdbcTemplate.query(sql, criteria, rowMapper);
    }

    public Optional<T> getById(long id) {
        String sql = getSelectSql() + " WHERE id = ?";
        try {
            T entity = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(entity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<T> getByPrimaryKey(long id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getPrimaryKeyColumn() + " = ?";
        try {
            T entity = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM " + getTableName();
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return (result != null) ? result : 0;
    }

    // ############################################################################################################

    private enum OperationType {
        INSERT,
        UPDATE,
        DELETE
    }

    public long insert(T entity) {
        String tableName = getTableName();
        Map<String, Object> values = extractColumnValues(entity);

        // Insertar la entidad principal
        String sql = buildInsertSql(tableName, values);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(values), keyHolder);
        long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        // Manejar relaciones
        handleRelations(entity, generatedId, OperationType.INSERT);

        return generatedId;
    }

    public void update(T entity) {
        String tableName = getTableName();
        Map<String, Object> values = extractColumnValues(entity);
        String primaryKey = getPrimaryKeyColumn();

        // Actualizar la entidad principal
        String sql = buildUpdateSql(tableName, values, primaryKey);
        namedParameterJdbcTemplate.update(sql, values);

        // Manejar relaciones
        handleRelations(entity, (Long) values.get(primaryKey), OperationType.UPDATE);
    }

    public void delete(long id) {
        String tableName = getTableName();
        String primaryKey = getPrimaryKeyColumn();

        // Manejar relaciones antes de borrar
        handleRelationsBeforeDelete(id);

        // Borrar la entidad principal
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = ?";
        jdbcTemplate.update(sql, id);
    }

    // Manejar relaciones (sin usar getDao)
    private void handleRelations(T entity, long parentId, OperationType operationType) {
        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                if (field.isAnnotationPresent(OneToOne.class)) {
                    handleOneToOneRelation(entity, field, parentId, operationType);
                } else if (field.isAnnotationPresent(OneToMany.class)) {
                    handleOneToManyRelation(entity, field, parentId, operationType);
                } else if (field.isAnnotationPresent(ManyToMany.class)) {
                    handleManyToManyRelation(entity, field, parentId, operationType);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to handle relation for field: " + field.getName(), e);
            }
        }
    }

    private void handleOneToOneRelation(T entity, Field field, long parentId, OperationType operationType) throws IllegalAccessException {
        OneToOne annotation = field.getAnnotation(OneToOne.class);
        Object relatedEntity = field.get(entity);

        if (relatedEntity != null) {
            Map<String, Object> relatedValues = extractColumnValues(relatedEntity);
            String relatedTableName = getTableName(relatedEntity.getClass());
            String joinColumn = annotation.joinColumn(); // Columna de la relación en la tabla principal

            // Si la relación ya tiene un ID (entidad referenciada ya existente)
            if (relatedValues.containsKey(getPrimaryKeyColumn(relatedEntity.getClass())) &&
                    relatedValues.get(getPrimaryKeyColumn(relatedEntity.getClass())) != null) {

                // La entidad ya tiene un ID, no insertamos en la tabla referenciada, solo asignamos la relación
                long relatedId = (Long) relatedValues.get(getPrimaryKeyColumn(relatedEntity.getClass()));

                // Actualizar la tabla principal con el ID de la entidad referenciada
                String updateSql = "UPDATE " + getTableName() + " SET " + joinColumn + " = ? WHERE " + getPrimaryKeyColumn() + " = ?";
                jdbcTemplate.update(updateSql, relatedId, parentId);

            } else if (operationType == OperationType.INSERT) {
                // Si no tiene un ID, es una entidad nueva que debe ser insertada primero
                String sql = buildInsertSql(relatedTableName, relatedValues);
                KeyHolder keyHolder = new GeneratedKeyHolder();
                namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(relatedValues), keyHolder);
                long relatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

                // Luego asociamos la entidad referenciada en la tabla principal
                String updateSql = "UPDATE " + getTableName() + " SET " + joinColumn + " = ? WHERE " + getPrimaryKeyColumn() + " = ?";
                jdbcTemplate.update(updateSql, relatedId, parentId);
            }
        }
    }

    private void handleOneToManyRelation(T entity, Field field, long parentId, OperationType operationType) throws IllegalAccessException {
        OneToMany annotation = field.getAnnotation(OneToMany.class);
        Collection<?> relatedEntities = (Collection<?>) field.get(entity);
        if (relatedEntities != null) {
            for (Object relatedEntity : relatedEntities) {
                Map<String, Object> relatedValues = extractColumnValues(relatedEntity);
                String relatedTableName = getTableName(relatedEntity.getClass());
                String mappedBy = annotation.mappedBy();

                // Configurar la clave foránea en la entidad relacionada
                relatedValues.put(mappedBy, parentId);

                if (operationType == OperationType.INSERT) {
                    String sql = buildInsertSql(relatedTableName, relatedValues);
                    namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(relatedValues));
                } else if (operationType == OperationType.UPDATE) {
                    String primaryKey = getPrimaryKeyColumn(relatedEntity.getClass());
                    String sql = buildUpdateSql(relatedTableName, relatedValues, primaryKey);
                    namedParameterJdbcTemplate.update(sql, relatedValues);
                }
            }
        }
    }

    private void handleManyToManyRelation(T entity, Field field, long parentId, OperationType operationType) throws IllegalAccessException {
        ManyToMany annotation = field.getAnnotation(ManyToMany.class);
        Collection<?> relatedEntities = (Collection<?>) field.get(entity);
        String joinTable = annotation.joinTable();
        String joinColumn = annotation.joinColumn();
        String inverseJoinColumn = annotation.inverseJoinColumn();

        if (operationType == OperationType.UPDATE || operationType == OperationType.DELETE) {
            // Eliminar relaciones existentes antes de actualizaciones o eliminaciones
            String deleteSql = "DELETE FROM " + joinTable + " WHERE " + joinColumn + " = ?";
            jdbcTemplate.update(deleteSql, parentId);
        }

        if (operationType != OperationType.DELETE && relatedEntities != null) {
            for (Object relatedEntity : relatedEntities) {
                Map<String, Object> relatedValues = extractColumnValues(relatedEntity);
                String relatedTableName = getTableName(relatedEntity.getClass());

                // Obtener el ID de la entidad relacionada
                long relatedId = (Long) relatedValues.get(getPrimaryKeyColumn(relatedEntity.getClass()));

                // Verificar si la relación ya existe en la tabla de unión
                String checkSql = "SELECT COUNT(*) FROM " + joinTable + " WHERE " + joinColumn + " = ? AND " + inverseJoinColumn + " = ?";
                int count = jdbcTemplate.queryForObject(checkSql, Integer.class, parentId, relatedId);

                if (count == 0) {
                    // Insertar en la tabla de unión solo si no existe la relación
                    String insertJoinSql = "INSERT INTO " + joinTable + " (" + joinColumn + ", " + inverseJoinColumn + ") VALUES (?, ?)";
                    jdbcTemplate.update(insertJoinSql, parentId, relatedId);
                }
            }
        }
    }

    private void handleRelationsBeforeDelete(long parentId) {
        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ManyToMany.class)) {
                ManyToMany annotation = field.getAnnotation(ManyToMany.class);
                String joinTable = annotation.joinTable();
                String joinColumn = annotation.joinColumn();

                // Eliminar las relaciones de la tabla de unión
                String deleteSql = "DELETE FROM " + joinTable + " WHERE " + joinColumn + " = ?";
                jdbcTemplate.update(deleteSql, parentId);
            }
        }
    }

    // Métodos auxiliares para SQL y extracción de columnas (como antes)
    private String buildInsertSql(String tableName, Map<String, Object> values) {
        String columns = String.join(", ", values.keySet());
        String placeholders = String.join(", ", values.keySet().stream().map(key -> ":" + key).toList());
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }

    private String buildUpdateSql(String tableName, Map<String, Object> values, String primaryKey) {
        String setClause = values.keySet().stream()
                .filter(key -> !key.equals(primaryKey))
                .map(key -> key + " = :" + key)
                .collect(Collectors.joining(", "));
        return "UPDATE " + tableName + " SET " + setClause + " WHERE " + primaryKey + " = :" + primaryKey;
    }

    private Map<String, Object> extractColumnValues(Object entity) {
        Map<String, Object> values = new HashMap<>();
        Class<?> clazz = entity.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                try {
                    String columnName = getColumnName(field);
                    Object value = field.get(entity);

                    // Excluir claves primarias nulas/autogeneradas
                    if (columnName.equals(getPrimaryKeyColumn(clazz)) && value == null) {
                        continue;
                    }
                    values.put(columnName, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to extract value for column: " + field.getName(), e);
                }
            }
        }
        return values;
    }

    private String getTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = entityClass.getAnnotation(Table.class);
            return tableAnnotation.name();
        }
        return entityClass.getSimpleName().toLowerCase();
    }

    private String getPrimaryKeyColumn(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                return getColumnName(field);
            }
        }
        throw new IllegalArgumentException("No primary key found for " + entityClass.getSimpleName());
    }


    // ############################################################################################################


    protected String getPrimaryKeyColumn() {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                return getColumnName(field);
            }
        }
        throw new IllegalArgumentException("No primary key found for " + entityClass.getSimpleName());
    }

    protected String getColumnName(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }

        Column columnAnnotation = field.getAnnotation(Column.class);

        if (columnAnnotation != null) {
            return columnAnnotation.name();
        }

        // Si no tiene la anotación @Column, usamos el nombre del campo
        return field.getName();
    }


    private String getTableName() {
        // Asegúrate de que este método devuelve el nombre correcto de la tabla
        // para la clase de entidad que se está insertando.
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = entityClass.getAnnotation(Table.class);
            return tableAnnotation.name();
        }
        // Si no está anotada, usa un nombre basado en el nombre de la clase (con alguna convención si es necesario)
        return entityClass.getSimpleName().toLowerCase();
    }

    protected String getSelectSql() {
        String tableName = getTableName();
        return "SELECT * FROM " + tableName;
    }

    private String buildSqlWithCriteria(String baseSql, Map<String, Object> criteria) {
        if (criteria.isEmpty()) {
            return baseSql;
        }

        StringBuilder sql = new StringBuilder(baseSql);
        sql.append(" WHERE ");

        criteria.forEach((key, value) -> sql.append(key).append(" = :").append(key).append(" AND "));
        sql.delete(sql.length() - 4, sql.length());

        return sql.toString();
    }
}
