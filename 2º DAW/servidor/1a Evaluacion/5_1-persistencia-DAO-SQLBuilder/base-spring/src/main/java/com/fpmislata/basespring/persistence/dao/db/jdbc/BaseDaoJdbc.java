package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Column;
import com.fpmislata.basespring.common.annotation.persistence.PrimaryKey;
import com.fpmislata.basespring.common.annotation.persistence.Table;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseDaoJdbc<T> {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GenericRowMapper<T> rowMapper;
    protected final Class<T> entityClass;


    public BaseDaoJdbc(JdbcTemplate jdbcTemplate, Class<T> entityClass) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.rowMapper = new GenericRowMapper<>(entityClass, jdbcTemplate);
        this.entityClass = entityClass;
    }

    // Metodo para obtener todas las entidades
    public List<T> getAll() {
        String sql = getSelectSql();
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Metodo para obtener todas las entidades con paginación
    public List<T> getAll(int page, int size) {
        String sql = getSelectSql() + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, size, (page - 1) * size);
    }

    // Metodo para obtener entidades por lista de ids
    public List<T> findAllById(Long[] ids) {
        String sql = getSelectSql() + " WHERE id IN (:ids)";
        Map<String, List<Long>> params = Map.of("ids", Arrays.asList(ids));
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    // Metodo para obtener entidades por criterios (parámetros dinámicos)
    public List<T> getByCriteria(Map<String, Object> criteria) {
        String sql = buildSqlWithCriteria(getSelectSql(), criteria);
        return namedParameterJdbcTemplate.query(sql, criteria, rowMapper);
    }

    // Metodo para obtener una entidad por su ID
    public Optional<T> findById(long id) {
        String sql = getSelectSql() + " WHERE id = ?";
        try {
            T entity = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(entity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // Buscar por ID
    public Optional<T> findByPrimaryKey(long id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getPrimaryKeyColumn() + " = ?";
        RowMapper<T> rowMapper = new GenericRowMapper<>(entityClass, jdbcTemplate);

        try {
            T entity = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Metodo genérico para construir el SQL de selección (puede ser sobrescrito por clases hijas si es necesario)
    protected String getSelectSql() {
        // Se obtiene el nombre de la tabla de la anotación @Table
        String tableName = getTableName();
        return "SELECT * FROM " + tableName;
    }

    // Construcción dinámica de SQL para criterios
    private String buildSqlWithCriteria(String baseSql, Map<String, Object> criteria) {
        if (criteria.isEmpty()) {
            return baseSql;
        }

        StringBuilder sql = new StringBuilder(baseSql);
        sql.append(" WHERE ");

        // Construir condiciones basadas en los criterios proporcionados
        criteria.forEach((key, value) -> sql.append(key).append(" = :").append(key).append(" AND "));

        // Eliminar el último "AND" innecesario
        sql.delete(sql.length() - 4, sql.length());

        return sql.toString();
    }

    // Obtener el nombre de la tabla utilizando reflexión
    private String getTableName() {
        Table tableAnnotation = getEntityClass().getAnnotation(Table.class);
        return tableAnnotation != null ? tableAnnotation.name() : entityClass.getSimpleName().toLowerCase();
        /*if (tableAnnotation != null) {
            return tableAnnotation.name();
        }
        throw new IllegalArgumentException("La entidad no tiene la anotación @Table");*/
    }

    // Contar el número de registros
    public int count() {
        String sql = "SELECT COUNT(*) FROM " + getTableName();
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return (result != null) ? result : 0;
    }


    // Insertar un objeto en la base de datos
    public long insert(T entity) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + getTableName() + " (");

        StringBuilder values = new StringBuilder();
        Object[] params = new Object[entity.getClass().getDeclaredFields().length];
        int paramIndex = 0;

        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                sql.append(getColumnName(field)).append(", ");
                values.append("?, ");
                field.setAccessible(true);
                try {
                    params[paramIndex++] = field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field " + field.getName(), e);
                }
            }
        }

        sql.setLength(sql.length() - 2);  // Eliminar la última coma
        values.setLength(values.length() - 2);  // Eliminar la última coma

        sql.append(") VALUES (").append(values).append(")");
        return jdbcTemplate.update(sql.toString(), params);
    }

    // Eliminar por ID
    public void delete(long id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getPrimaryKeyColumn() + " = ?";
        jdbcTemplate.update(sql, id);
    }

    // Actualizar un objeto
    public void update(T entity) {
        StringBuilder sql = new StringBuilder("UPDATE " + getTableName() + " SET ");

        Object[] params = new Object[entity.getClass().getDeclaredFields().length];
        int paramIndex = 0;

        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(PrimaryKey.class)) {
                sql.append(getColumnName(field)).append(" = ?, ");
                field.setAccessible(true);
                try {
                    params[paramIndex++] = field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field " + field.getName(), e);
                }
            }
        }

        sql.setLength(sql.length() - 2);  // Eliminar la última coma
        sql.append(" WHERE ").append(getPrimaryKeyColumn()).append(" = ?");

        try {
            Field primaryKeyField = entity.getClass().getDeclaredField(getPrimaryKeyColumn());
            primaryKeyField.setAccessible(true);
            params[paramIndex] = primaryKeyField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing primary key field", e);
        }

        jdbcTemplate.update(sql.toString(), params);
    }

    // Obtener la clase de la entidad
    protected abstract Class<T> getEntityClass();


    protected String getColumnName(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        return columnAnnotation != null ? columnAnnotation.name() : field.getName();
    }

    protected String getPrimaryKeyColumn() {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                return getColumnName(field);
            }
        }
        throw new IllegalArgumentException("No primary key found for " + entityClass.getSimpleName());
    }
}
