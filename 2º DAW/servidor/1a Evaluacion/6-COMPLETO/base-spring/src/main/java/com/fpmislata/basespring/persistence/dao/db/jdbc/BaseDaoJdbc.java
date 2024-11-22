package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.persistence.dao.db.GenericDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.metadata.EntityMetadataExtractor;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.operation.OperationType;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.relation.RelationHandler;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.sql.SqlBuilder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.*;

public class BaseDaoJdbc<T> implements GenericDaoDb<T> {

    // Variables de instancia
    protected final Class<T> entityClass; // Representa la clase de la entidad generica
    private final JdbcTemplate jdbcTemplate; // Permite ejecutar consultas SQL
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate; // Permite el uso de parametros nombrados en consultas
    private final GenericRowMapper<T> rowMapper; // Mapea las filas del resultado SQL a objetos de la clase generica
    private final EntityMetadataExtractor<T> entityMetadataExtractor; // Extrae los valores de las columnas de una entidad en un mapa clave-valor
    private final RelationHandler<T> relationHandler; // Maneja las relaciones entre entidades
    private final SqlBuilder sqlBuilder; // Construye SQL de inserción, actualización y criterios WHERE

    // Constructor
    // Inicializa las variables de instancia con los parametros proporcionados
    public BaseDaoJdbc(Class<T> entityClass, DataSource dataSource) {
        this.entityClass = entityClass;
        this.relationHandler = new RelationHandler<>(entityClass, dataSource);
        this.entityMetadataExtractor = new EntityMetadataExtractor<>(entityClass);
        this.sqlBuilder = new SqlBuilder();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource); // dataSource es un objeto que representa la fuente de datos
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
        return jdbcTemplate.query(entityMetadataExtractor.getSelectSql(), rowMapper);
    }

    // Recupera una pagina especifica de entidades
    public List<T> getAll(int page, int size) {
        String sql = entityMetadataExtractor.getSelectSql() + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, size, (page - 1) * size);
    }

    // Recupera entidades por un array de IDs
    public List<T> getAllByIds(Long[] ids) {
        String sql = entityMetadataExtractor.getSelectSql() + " WHERE id IN (:ids)";
        Map<String, List<Long>> params = Map.of("ids", Arrays.asList(ids));
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    // Recupera entidades segun un mapa de criterios
    public List<T> getByCriteria(Map<String, Object> criteria) {
        String sql = sqlBuilder.buildSqlWithCriteria(entityMetadataExtractor.getSelectSql(), criteria);
        return namedParameterJdbcTemplate.query(sql, criteria, rowMapper);
    }

    // Recupera una entidad por su ID como un Optional
    public Optional<T> getById(long id) {
        return queryForOptional(entityMetadataExtractor.getSelectSql() + " WHERE id = ?", id);
    }

    // Recupera una entidad usando su clave primaria
    public Optional<T> getByPrimaryKey(long id) {
        return queryForOptional("SELECT * FROM " + entityMetadataExtractor.getTableName() + " WHERE " + entityMetadataExtractor.getPrimaryKeyColumn() + " = ?", id);
    }

    // Cuenta el numero total de entidades en la tabla
    public int count() {
        String sql = "SELECT COUNT(*) FROM " + entityMetadataExtractor.getTableName();
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
        Map<String, Object> values = entityMetadataExtractor.extractColumnValues(entity);
        String sql = sqlBuilder.buildInsertSql(entityMetadataExtractor.getTableName(), values);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(values), keyHolder);
        long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        relationHandler.handleRelations(entity, generatedId, OperationType.INSERT);
        return generatedId;
    }

    // Actualiza una entidad existente en la base de datos
    public void update(T entity) {
        Map<String, Object> values = entityMetadataExtractor.extractColumnValues(entity);
        String sql = sqlBuilder.buildUpdateSql(entityMetadataExtractor.getTableName(), values, entityMetadataExtractor.getPrimaryKeyColumn());
        namedParameterJdbcTemplate.update(sql, values);
        relationHandler.handleRelations(entity, (Long) values.get(entityMetadataExtractor.getPrimaryKeyColumn()), OperationType.UPDATE);
    }

    // Elimina una entidad por su ID
    public void delete(long id) {
        relationHandler.handleRelationsBeforeDelete(id);
        String sql = "DELETE FROM " + entityMetadataExtractor.getTableName() + " WHERE " + entityMetadataExtractor.getPrimaryKeyColumn() + " = ?";
        jdbcTemplate.update(sql, id);
    }

    public String select() {
        return entityMetadataExtractor.getSelectSql();
    }

}