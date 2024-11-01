package com.fpmislata.basespring.persistence.repository.impl.jdbc.common;

import com.fpmislata.basespring.common.exception.DatabaseOperationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
public class DynamicSQLBuilder<T> {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String tableName; // Nombre de la tabla
    private final List<String> idColumns; // Lista de columnas identificadoras
    private final RowMapper<T> rowMapper; // El mapeador de filas

    // Busca por ID (o cualquier columna definida como identificador)
    public Optional<T> findById(String columnName, Object id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = :id", tableName, columnName);
        Map<String, Object> params = Map.of("id", id);
        try {
            List<T> results = namedParameterJdbcTemplate.query(sql, params, rowMapper);
            return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
        } catch (Exception e) {
            throw new DatabaseOperationException("Error buscando entidad por " + columnName + " en " + tableName, e);
        }
    }

    // Obtiene todos los registros
    public List<T> findAll() {
        String sql = String.format("SELECT * FROM %s", tableName);
        try {
            return jdbcTemplate.query(sql, rowMapper);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error obteniendo todos los registros de " + tableName, e);
        }
    }

    // Guarda o actualiza una entidad
    @Transactional
    public void save(Map<String, Object> fields, Object id) {
        if (id != null) {
            update(fields, id);
        } else {
            insert(fields);
        }
    }

    // Inserta una entidad
    private void insert(Map<String, Object> fields) {
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName,
                String.join(", ", fields.keySet()),
                ":" + String.join(", :", fields.keySet()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(fields);

        try {
            namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder);

            Long generatedId = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
            if (generatedId == null) {
                throw new DatabaseOperationException("No se generó un ID para la nueva entidad en " + tableName);
            }
            fields.put(idColumns.get(0), generatedId); // Suponemos que el primer ID es el que se espera
        } catch (Exception e) {
            throw new DatabaseOperationException("Error de inserción en " + tableName, e);
        }
    }

    // Actualiza una entidad
    private void update(Map<String, Object> fields, Object id) {
        fields.put(idColumns.get(0), id); // Se usa la primera columna identificadora
        String setClause = fields.keySet().stream()
                .filter(key -> !idColumns.contains(key)) // Excluimos todas las columnas de ID
                .map(key -> key + " = :" + key)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        String sql = String.format("UPDATE %s SET %s WHERE %s = :%s", tableName, setClause, idColumns.get(0), idColumns.get(0));
        try {
            namedParameterJdbcTemplate.update(sql, fields);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error actualizando entidad en " + tableName, e);
        }
    }

    // Borra una entidad por una columna identificadora específica
    public void deleteById(String columnName, Object id) {
        String sql = String.format("DELETE FROM %s WHERE %s = :id", tableName, columnName);
        Map<String, Object> params = Map.of("id", id);
        try {
            namedParameterJdbcTemplate.update(sql, params);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error eliminando entidad por " + columnName + " en " + tableName, e);
        }
    }

    // Busca por ISBN (o cualquier otra columna)
    public Optional<T> findByColumn(String columnName, Object value) {
        String sql = String.format("SELECT * FROM %s WHERE %s = :value", tableName, columnName);
        Map<String, Object> params = Map.of("value", value);
        try {
            List<T> results = namedParameterJdbcTemplate.query(sql, params, rowMapper);
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            throw new DatabaseOperationException("Error buscando entidades por " + columnName + " en " + tableName, e);
        }
    }

    // Eejecuta consultas personalizadas
    public List<T> executeCustomQuery(String sql, Map<String, Object> params) {
        try {
            return namedParameterJdbcTemplate.query(sql, params, rowMapper);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error ejecutando consulta personalizada en " + tableName, e);
        }
    }

    // Busca por criterios
    public List<T> findByCriteria(String selectClause, String joinClause, Map<String, Object> criteria) {
        StringBuilder sql = new StringBuilder(selectClause + " FROM " + tableName + " " + joinClause);
        Map<String, Object> params = new HashMap<>();

        if (criteria != null && !criteria.isEmpty()) {
            sql.append(" WHERE ");
            criteria.forEach((key, value) -> {
                if (value instanceof List) {
                    sql.append(key).append(" IN (:").append(key).append(") AND ");
                    params.put(key, value);
                } else {
                    sql.append(key).append(" = :").append(key).append(" AND ");
                    params.put(key, value);
                }
            });
            sql.setLength(sql.length() - 5); // Remove the last " AND "
        }

        return namedParameterJdbcTemplate.query(sql.toString(), params, rowMapper);
    }

    // Método para manejar consultas por ID
    public List<T> findByIds(List<Object> ids) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");
        Map<String, Object> params = new HashMap<>();

        for (String idColumn : idColumns) {
            sql.append(idColumn).append(" IN (:").append(idColumn).append(") OR ");
            params.put(idColumn, ids);
        }
        sql.setLength(sql.length() - 4); // Remove the last " OR "

        return namedParameterJdbcTemplate.query(sql.toString(), params, rowMapper);
    }


    // Cuenta todos los registros
    public int count() {
        String sql = String.format("SELECT COUNT(*) FROM %s", tableName);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }


    // Obtiene todos los registros con paginación
    public List<T> findAllWithPagination(int page, int size) {
        String sql = String.format("SELECT * FROM %s LIMIT :size OFFSET :offset", tableName);
        Map<String, Object> params = Map.of("size", size, "offset", page * size);
        return executeQuery(sql, params);
    }

    // Método genérico para ejecutar consultas y devolver una lista
    protected List<T> executeQuery(String sql, Map<String, Object> params) {
        try {
            return namedParameterJdbcTemplate.query(sql, params, rowMapper);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error ejecutando la consulta en " + tableName, e);
        }
    }

    // Método genérico para ejecutar consultas y devolver un Optional
    protected Optional<T> executeQueryForOptional(String sql, Map<String, Object> params) {
        try {
            List<T> results = namedParameterJdbcTemplate.query(sql, params, rowMapper);
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            throw new DatabaseOperationException("Error ejecutando la consulta en " + tableName, e);
        }
    }

    public void insertIntoRelation(String relationTable, Map<String, Object> fields) {
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                relationTable,
                String.join(", ", fields.keySet()),
                ":" + String.join(", :", fields.keySet()));

        try {
            namedParameterJdbcTemplate.update(sql, fields);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error insertando en la tabla de relación " + relationTable, e);
        }
    }
}
