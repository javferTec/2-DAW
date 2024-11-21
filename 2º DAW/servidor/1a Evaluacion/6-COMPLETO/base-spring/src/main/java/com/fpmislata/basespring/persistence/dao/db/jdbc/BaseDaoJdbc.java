package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.*;
import com.fpmislata.basespring.persistence.dao.db.GenericDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.cache.ReflectionColumnFieldCache;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
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

    // Enum de tipos de operaciones
    private enum OperationType {
        INSERT, UPDATE, DELETE
    }

    // Variables de instancia
    protected final Class<T> entityClass; // Representa la clase de la entidad generica
    private final JdbcTemplate jdbcTemplate; // Permite ejecutar consultas SQL
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate; // Permite el uso de parametros nombrados en consultas
    private final GenericRowMapper<T> rowMapper; // Mapea las filas del resultado SQL a objetos de la clase generica

    // Constructor
    // Inicializa las variables de instancia con los parametros proporcionados
    public BaseDaoJdbc(Class<T> entityClass, DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource); // dataSource es un objeto que representa la fuente de datos
        this.entityClass = entityClass;
        this.rowMapper = new GenericRowMapper<>(entityClass, jdbcTemplate);
    }

    // ##################### Generic Query Methods #####################

    // Ejecuta una consulta SQL personalizada que devuelve una unica entidad
    public T customSqlQuery(String sql, Map<String, ?> params) {
        try {
            return (params != null && !params.isEmpty())
                    ? namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper)
                    : jdbcTemplate.queryForObject(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Ejecuta una consulta SQL personalizada que devuelve una lista de entidades
    public List<T> customSqlQueryForList(String sql, Map<String, ?> params) {
        return (params != null && !params.isEmpty())
                ? namedParameterJdbcTemplate.query(sql, params, rowMapper)
                : jdbcTemplate.query(sql, rowMapper);
    }

    // Recupera todas las entidades de la tabla asociada
    public List<T> getAll() {
        return jdbcTemplate.query(getSelectSql(), rowMapper);
    }

    // Recupera una pagina especifica de entidades
    public List<T> getAll(int page, int size) {
        String sql = getSelectSql() + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, size, (page - 1) * size);
    }

    // Recupera entidades por un array de IDs
    public List<T> getAllByIds(Long[] ids) {
        String sql = getSelectSql() + " WHERE id IN (:ids)";
        Map<String, List<Long>> params = Map.of("ids", Arrays.asList(ids));
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    // Recupera entidades segun un mapa de criterios
    public List<T> getByCriteria(Map<String, Object> criteria) {
        String sql = buildSqlWithCriteria(getSelectSql(), criteria);
        return namedParameterJdbcTemplate.query(sql, criteria, rowMapper);
    }

    // Recupera una entidad por su ID como un Optional
    public Optional<T> getById(long id) {
        return queryForOptional(getSelectSql() + " WHERE id = ?", id);
    }

    // Recupera una entidad usando su clave primaria
    public Optional<T> getByPrimaryKey(long id) {
        return queryForOptional("SELECT * FROM " + getTableName() + " WHERE " + getPrimaryKeyColumn() + " = ?", id);
    }

    // Cuenta el numero total de entidades en la tabla
    public int count() {
        String sql = "SELECT COUNT(*) FROM " + getTableName();
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }

    // Realiza una consulta que devuelve un Optional
    private Optional<T> queryForOptional(String sql, Object... params) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, params));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // ##################### CRUD Operations #####################

    // Inserta una nueva entidad en la base de datos
    public long insert(T entity) {
        Map<String, Object> values = extractColumnValues(entity);
        String sql = buildInsertSql(getTableName(), values);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(values), keyHolder);
        long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        handleRelations(entity, generatedId, OperationType.INSERT);
        return generatedId;
    }

    // Actualiza una entidad existente en la base de datos
    public void update(T entity) {
        Map<String, Object> values = extractColumnValues(entity);
        String sql = buildUpdateSql(getTableName(), values, getPrimaryKeyColumn());
        namedParameterJdbcTemplate.update(sql, values);
        handleRelations(entity, (Long) values.get(getPrimaryKeyColumn()), OperationType.UPDATE);
    }

    // Elimina una entidad por su ID
    public void delete(long id) {
        handleRelationsBeforeDelete(id);
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getPrimaryKeyColumn() + " = ?";
        jdbcTemplate.update(sql, id);
    }


    // ##################### Relation Handling #####################

    // Maneja las relaciones entre entidades cuando se realiza una operación (INSERT, UPDATE, DELETE)
    private void handleRelations(T entity, long parentId, OperationType operationType) {
        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true); // Permite el acceso a campos privados
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

    // Maneja relaciones OneToOne al insertar o actualizar una entidad
    private void handleOneToOneRelation(T entity, Field field, long parentId, OperationType operationType) throws IllegalAccessException {
        OneToOne annotation = field.getAnnotation(OneToOne.class);
        Object relatedEntity = field.get(entity); // Obtiene la entidad relacionada

        if (relatedEntity != null) {
            Map<String, Object> relatedValues = extractColumnValues(relatedEntity);
            String relatedTableName = getTableName(relatedEntity.getClass());
            String joinColumn = annotation.joinColumn(); // Columna de la relación en la tabla principal

            if (relatedValues.containsKey(getPrimaryKeyColumn(relatedEntity.getClass())) &&
                    relatedValues.get(getPrimaryKeyColumn(relatedEntity.getClass())) != null) {
                // La entidad relacionada ya existe, actualizamos la tabla principal con su ID
                long relatedId = (Long) relatedValues.get(getPrimaryKeyColumn(relatedEntity.getClass()));
                String updateSql = "UPDATE " + getTableName() + " SET " + joinColumn + " = ? WHERE " + getPrimaryKeyColumn() + " = ?";
                jdbcTemplate.update(updateSql, relatedId, parentId);

            } else if (operationType == OperationType.INSERT) {
                // Si es una nueva entidad relacionada, primero la insertamos
                String sql = buildInsertSql(relatedTableName, relatedValues);
                KeyHolder keyHolder = new GeneratedKeyHolder();
                namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(relatedValues), keyHolder);
                long relatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

                // Luego actualizamos la tabla principal con el ID de la entidad relacionada
                String updateSql = "UPDATE " + getTableName() + " SET " + joinColumn + " = ? WHERE " + getPrimaryKeyColumn() + " = ?";
                jdbcTemplate.update(updateSql, relatedId, parentId);
            }
        }
    }

    // Maneja relaciones OneToMany al insertar o actualizar una entidad
    private void handleOneToManyRelation(T entity, Field field, long parentId, OperationType operationType) throws IllegalAccessException {
        OneToMany annotation = field.getAnnotation(OneToMany.class);
        Collection<?> relatedEntities = (Collection<?>) field.get(entity); // Obtiene las entidades relacionadas
        if (relatedEntities != null) {
            for (Object relatedEntity : relatedEntities) {
                Map<String, Object> relatedValues = extractColumnValues(relatedEntity);
                String relatedTableName = getTableName(relatedEntity.getClass());
                String mappedBy = annotation.mappedBy(); // Clave foránea que conecta las tablas

                relatedValues.put(mappedBy, parentId); // Configura la clave foránea en la entidad relacionada

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

    // Maneja relaciones ManyToMany al insertar o actualizar una entidad
    private void handleManyToManyRelation(T entity, Field field, long parentId, OperationType operationType) throws IllegalAccessException {
        ManyToMany annotation = field.getAnnotation(ManyToMany.class);
        Collection<?> relatedEntities = (Collection<?>) field.get(entity); // Obtiene las entidades relacionadas
        String joinTable = annotation.joinTable(); // Tabla de unión
        String joinColumn = annotation.joinColumn(); // Columna de unión de la tabla principal
        String inverseJoinColumn = annotation.inverseJoinColumn(); // Columna de unión de la entidad relacionada

        if (operationType == OperationType.UPDATE || operationType == OperationType.DELETE) {
            // Elimina relaciones existentes antes de una actualización o eliminación
            String deleteSql = "DELETE FROM " + joinTable + " WHERE " + joinColumn + " = ?";
            jdbcTemplate.update(deleteSql, parentId);
        }

        if (operationType != OperationType.DELETE && relatedEntities != null) {
            for (Object relatedEntity : relatedEntities) {
                Map<String, Object> relatedValues = extractColumnValues(relatedEntity);
                long relatedId = (Long) relatedValues.get(getPrimaryKeyColumn(relatedEntity.getClass())); // Obtiene el ID de la entidad relacionada

                String checkSql = "SELECT COUNT(*) FROM " + joinTable + " WHERE " + joinColumn + " = ? AND " + inverseJoinColumn + " = ?";
                int count = jdbcTemplate.queryForObject(checkSql, Integer.class, parentId, relatedId);

                if (count == 0) {
                    // Inserta en la tabla de unión si la relación no existe
                    String insertJoinSql = "INSERT INTO " + joinTable + " (" + joinColumn + ", " + inverseJoinColumn + ") VALUES (?, ?)";
                    jdbcTemplate.update(insertJoinSql, parentId, relatedId);
                }
            }
        }
    }

    // Maneja relaciones ManyToMany antes de eliminar una entidad
    private void handleRelationsBeforeDelete(long parentId) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToMany.class)) {
                ManyToMany annotation = field.getAnnotation(ManyToMany.class);
                String sql = "DELETE FROM " + annotation.joinTable() + " WHERE " + annotation.joinColumn() + " = ?";
                jdbcTemplate.update(sql, parentId);
            }
        }
    }

    // ##################### SQL Builders #####################

    // Construye un SQL de inserción basado en el nombre de la tabla y los valores proporcionados
    private String buildInsertSql(String tableName, Map<String, Object> values) {
        String columns = String.join(", ", values.keySet()); // Obtiene los nombres de las columnas
        String placeholders = values.keySet().stream().map(key -> ":" + key).collect(Collectors.joining(", ")); // Genera los marcadores de posición
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")"; // Retorna el SQL de inserción
    }

    // Construye un SQL de actualización basado en el nombre de la tabla, los valores y la clave primaria
    private String buildUpdateSql(String tableName, Map<String, Object> values, String primaryKey) {
        String setClause = values.keySet().stream()
                .filter(key -> !key.equals(primaryKey)) // Excluye la clave primaria de la cláusula SET
                .map(key -> key + " = :" + key) // Genera las asignaciones columna = :valor
                .collect(Collectors.joining(", "));
        return "UPDATE " + tableName + " SET " + setClause + " WHERE " + primaryKey + " = :" + primaryKey; // Retorna el SQL de actualización
    }

    // Construye un SQL con criterios WHERE basado en un SQL base y los criterios proporcionados
    private String buildSqlWithCriteria(String baseSql, Map<String, Object> criteria) {
        if (criteria.isEmpty()) return baseSql; // Si no hay criterios, retorna el SQL base
        String whereClause = criteria.keySet().stream()
                .map(key -> key + " = :" + key) // Genera las condiciones WHERE clave = :valor
                .collect(Collectors.joining(" AND "));
        return baseSql + " WHERE " + whereClause; // Retorna el SQL completo con la cláusula WHERE
    }

    // ##################### Metadata Extraction #####################

    // Extrae los valores de las columnas de una entidad en un mapa clave-valor
    private Map<String, Object> extractColumnValues(Object entity) {
        Map<String, Object> values = new HashMap<>();
        ReflectionColumnFieldCache.getCachedFields(entity.getClass()).forEach((column, field) -> {
            try {
                Object value = field.get(entity); // Obtiene el valor del campo
                if (!column.equals(getPrimaryKeyColumn(entity.getClass())) || value != null) {
                    // Agrega el valor al mapa si no es la clave primaria o si no es nulo
                    values.put(column, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to extract value for column: " + column, e);
            }
        });
        return values; // Retorna el mapa de valores
    }

    // Obtiene el nombre de la columna correspondiente a la clave primaria de una clase
    private String getPrimaryKeyColumn(Class<?> entityClass) {
        return ReflectionColumnFieldCache.getCachedFields(entityClass).values().stream()
                .filter(field -> field.isAnnotationPresent(PrimaryKey.class)) // Filtra campos anotados como PrimaryKey
                .map(this::getColumnName) // Obtiene el nombre de la columna
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No primary key found for " + entityClass.getSimpleName())); // Lanza excepción si no encuentra clave primaria
    }

    // Sobrecarga que obtiene el nombre de la columna de la clave primaria de la entidad actual
    private String getPrimaryKeyColumn() {
        return getPrimaryKeyColumn(entityClass);
    }

    // Obtiene el nombre de la tabla correspondiente a una clase
    private String getTableName(Class<?> clazz) {
        return Optional.ofNullable(clazz.getAnnotation(Table.class))
                .map(Table::name) // Obtiene el nombre especificado en la anotación @Table
                .orElse(clazz.getSimpleName().toLowerCase()); // Retorna el nombre de la clase en minúsculas si no está anotada
    }

    // Sobrecarga que obtiene el nombre de la tabla de la entidad actual
    private String getTableName() {
        return getTableName(entityClass);
    }

    // Obtiene el nombre de la columna basado en su anotación o nombre del campo
    private String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null ? column.name() : field.getName(); // Retorna el nombre de la columna o el del campo
    }

    // Genera el SQL de selección de todos los registros para la tabla de la entidad actual
    protected String getSelectSql() {
        return "SELECT * FROM " + getTableName(); // Retorna el SQL SELECT
    }
}