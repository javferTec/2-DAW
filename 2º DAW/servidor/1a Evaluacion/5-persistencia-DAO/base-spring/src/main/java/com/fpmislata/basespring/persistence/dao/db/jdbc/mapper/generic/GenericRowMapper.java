package com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic;

import com.fpmislata.basespring.common.annotation.persistence.*;
import com.fpmislata.basespring.common.exception.MappingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
public class GenericRowMapper<T> implements RowMapper<T> {

    private static final Logger logger = LoggerFactory.getLogger(GenericRowMapper.class);
    private final Class<T> type;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public T mapRow(@NonNull ResultSet resultSet, int rowNum) {
        try {
            T instance = type.getDeclaredConstructor().newInstance();
            Map<String, Field> fields = FieldCache.getCachedFields(type);

            // Mapeo de campos simples
            for (Map.Entry<String, Field> entry : fields.entrySet()) {
                String columnName = getColumnName(entry.getValue());
                setFieldValue(instance, resultSet, entry.getValue(), columnName);
            }

            // Procesar relaciones
            processRelationships(resultSet, instance);

            return instance;
        } catch (Exception e) {
            logger.error("Error mapping row to {}: {}", type.getSimpleName(), e.getMessage(), e);
            throw new MappingException("Error mapping row to " + type.getSimpleName(), e);
        }
    }

    private String getColumnName(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        return columnAnnotation != null ? columnAnnotation.name() : field.getName();
    }

    private void setFieldValue(T instance, ResultSet resultSet, Field field, String columnName) throws Exception {
        String setterName = "set" + capitalize(field.getName());
        Method setter = type.getMethod(setterName, field.getType());

        if (columnExists(resultSet, columnName)) {
            Object value = resultSet.getObject(columnName, field.getType());
            logger.debug("Setting field '{}' with value '{}' from column '{}'", field.getName(), value, columnName);
            setter.invoke(instance, value);
        } else {
            logger.warn("Column '{}' does not exist in result set for field '{}' of entity {}", columnName, field.getName(), type.getSimpleName());
        }
    }

    private boolean columnExists(ResultSet resultSet, String columnName) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                if (metaData.getColumnName(i).equalsIgnoreCase(columnName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Error checking if column '{}' exists: {}", columnName, e.getMessage(), e);
        }
        return false;
    }

    private void processRelationships(ResultSet resultSet, Object instance) throws Exception {
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(OneToOne.class)) {
                try {
                    mapOneToOne(resultSet, instance, field);
                } catch (Exception e) {
                    logger.warn("Error mapping OneToOne relationship for field '{}': {}", field.getName(), e.getMessage());
                }
            } else if (field.isAnnotationPresent(OneToMany.class)) {
                mapOneToMany(instance, field);
            } else if (field.isAnnotationPresent(ManyToMany.class)) {
                mapManyToMany(instance, field);
            }
        }
    }

    private void mapOneToOne(ResultSet resultSet, Object instance, Field field) throws Exception {
        OneToOne annotation = field.getAnnotation(OneToOne.class);
        String joinColumn = annotation.joinColumn(); // Nombre de la columna FK
        Class<?> targetEntity = annotation.targetEntity();

        // Obtener el valor de la clave foránea (FK) desde el ResultSet
        Object foreignKeyValue = resultSet.getObject(joinColumn);
        if (foreignKeyValue != null) {
            String query = String.format("SELECT * FROM %s WHERE id = ?", getTableName(targetEntity));
            Object relatedInstance = jdbcTemplate.queryForObject(query, new Object[]{foreignKeyValue}, new GenericRowMapper<>(targetEntity, jdbcTemplate));
            setRelatedField(instance, field, relatedInstance);
        }
    }

    private void mapOneToMany(Object instance, Field field) throws Exception {
        OneToMany annotation = field.getAnnotation(OneToMany.class);

        // Verificar que el campo sea una colección
        if (!Collection.class.isAssignableFrom(field.getType())) {
            throw new MappingException("Field " + field.getName() + " must be a Collection for @OneToMany");
        }

        List<?> relatedList = mapRelatedEntities(annotation.targetEntity(), buildOneToManyQuery(annotation), instance);

        setRelatedField(instance, field, relatedList);
    }

    private void mapManyToMany(Object instance, Field field) throws Exception {
        ManyToMany annotation = field.getAnnotation(ManyToMany.class);
        List<?> relatedList = mapRelatedEntities(annotation.targetEntity(), buildManyToManyQuery(annotation), instance);

        setRelatedField(instance, field, relatedList);
    }

    private List<?> mapRelatedEntities(Class<?> targetEntity, String query, Object instance) {
        try {
            return jdbcTemplate.query(query, new Object[]{getPrimaryKey(instance)},
                    (rs, rowNum) -> new GenericRowMapper<>(targetEntity, jdbcTemplate).mapRow(rs, rowNum));
        } catch (Exception e) {
            logger.error("Error mapping related entities: {}", e.getMessage(), e);
            return Collections.emptyList(); // Devuelve una lista vacía en caso de error
        }
    }

    private String buildOneToManyQuery(OneToMany annotation) {
        return String.format("SELECT * FROM %s WHERE %s = ?",
                annotation.targetEntity().getSimpleName().toLowerCase(), annotation.mappedBy());
    }

    private String buildManyToManyQuery(ManyToMany annotation) {
        return String.format("SELECT t.* FROM %s t INNER JOIN %s jt ON t.id = jt.%s WHERE jt.%s = ?",
                getTableName(annotation.targetEntity()), annotation.joinTable(), annotation.inverseJoinColumn(), annotation.joinColumn());
    }

    private void setRelatedField(Object instance, Field field, Object value) throws Exception {
        Method setter = type.getMethod("set" + capitalize(field.getName()), field.getType());
        setter.invoke(instance, value);
    }

    private Object getPrimaryKey(Object instance) {
        return getPrimaryKeyField(instance)
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(instance);
                    } catch (IllegalAccessException e) {
                        throw new MappingException("Failed to access primary key field", e);
                    }
                })
                .orElseThrow(() -> new MappingException("No primary key found in " + instance.getClass()));
    }

    private Optional<Field> getPrimaryKeyField(Object instance) {
        return Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(PrimaryKey.class))
                .findFirst();
    }

    private String getTableName(Class<?> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        return tableAnnotation != null ? tableAnnotation.name() : entityClass.getSimpleName().toLowerCase();
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
