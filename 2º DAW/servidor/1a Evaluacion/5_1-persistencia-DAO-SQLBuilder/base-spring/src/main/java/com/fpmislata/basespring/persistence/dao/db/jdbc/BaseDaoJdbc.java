package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Column;
import com.fpmislata.basespring.common.annotation.persistence.PrimaryKey;
import com.fpmislata.basespring.common.annotation.persistence.Table;
import com.fpmislata.basespring.persistence.dao.db.GenericDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jdbc.cache.FieldCache;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseDaoJdbc<T> implements GenericDaoDb<T> {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GenericRowMapper<T> rowMapper;
    protected final Class<T> entityClass;

    public BaseDaoJdbc(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityClass = (Class<T>) type;
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

    private String getTableName() {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        return tableAnnotation != null ? tableAnnotation.name() : entityClass.getSimpleName().toLowerCase();
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM " + getTableName();
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return (result != null) ? result : 0;
    }

    public long insert(T entity) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + getTableName() + " (");
        StringBuilder values = new StringBuilder();
        Map<String, Field> fields = FieldCache.getCachedFields(entity.getClass());
        Object[] params = new Object[fields.size()];
        int index = 0;

        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            sql.append(entry.getKey()).append(", ");
            values.append("?, ");
            try {
                params[index++] = entry.getValue().get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field " + entry.getValue().getName(), e);
            }
        }

        sql.setLength(sql.length() - 2);
        values.setLength(values.length() - 2);
        sql.append(") VALUES (").append(values).append(")");

        return jdbcTemplate.update(sql.toString(), params);
    }

    public void delete(long id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getPrimaryKeyColumn() + " = ?";
        jdbcTemplate.update(sql, id);
    }

    public void update(T entity) {
        StringBuilder sql = new StringBuilder("UPDATE " + getTableName() + " SET ");
        Map<String, Field> fields = FieldCache.getCachedFields(entity.getClass());
        Object[] params = new Object[fields.size() + 1];
        int index = 0;

        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            if (!entry.getValue().isAnnotationPresent(PrimaryKey.class)) {
                sql.append(entry.getKey()).append(" = ?, ");
                try {
                    params[index++] = entry.getValue().get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field " + entry.getValue().getName(), e);
                }
            }
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE ").append(getPrimaryKeyColumn()).append(" = ?");

        try {
            Field primaryKeyField = entityClass.getDeclaredField(getPrimaryKeyColumn());
            primaryKeyField.setAccessible(true);
            params[index] = primaryKeyField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing primary key field", e);
        }

        jdbcTemplate.update(sql.toString(), params);
    }

    protected String getPrimaryKeyColumn() {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                return getColumnName(field);
            }
        }
        throw new IllegalArgumentException("No primary key found for " + entityClass.getSimpleName());
    }

    protected String getColumnName(Field field) {
        return field.getAnnotation(Column.class).name();
    }
}
