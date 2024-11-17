package com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic;

import com.fpmislata.basespring.common.annotation.persistence.*;
import com.fpmislata.basespring.common.exception.MappingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GenericRowMapper<T> implements RowMapper<T> {

    private final Class<T> type;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public T mapRow(@NonNull ResultSet resultSet, int rowNum) {
        try {
            T instance = type.getDeclaredConstructor().newInstance();
            Map<String, Field> fields = FieldCache.getCachedFields(type);

            for (Map.Entry<String, Field> entry : fields.entrySet()) {
                String columnName = entry.getKey();
                Field field = entry.getValue();

                // Obtener el nombre de la columna desde la anotación @Column
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null) {
                    columnName = columnAnnotation.name(); // Usar el nombre de columna de la anotación
                }

                String setterName = "set" + capitalize(field.getName());
                try {
                    Method setter = type.getMethod(setterName, field.getType());
                    if (columnExists(resultSet, columnName)) {
                        Object value = resultSet.getObject(columnName, field.getType());
                        setter.invoke(instance, value);
                    }
                } catch (NoSuchMethodException e) {
                    throw new MappingException("No setter found for field: " + field.getName(), e);
                }
            }

            // Procesa las relaciones
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(OneToOne.class)) {
                    mapOneToOne(resultSet, instance, field);
                } else if (field.isAnnotationPresent(OneToMany.class)) {
                    mapOneToMany(instance, field);
                } else if (field.isAnnotationPresent(ManyToMany.class)) {
                    mapManyToMany(instance, field);
                }
            }

            return instance;

        } catch (Exception e) {
            throw new MappingException("Error mapping row to " + type.getSimpleName(), e);
        }
    }

    private boolean columnExists(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void mapOneToOne(ResultSet resultSet, Object instance, Field field) throws Exception {
        OneToOne annotation = field.getAnnotation(OneToOne.class);
        Class<?> targetEntity = annotation.targetEntity();

        GenericRowMapper<?> relatedMapper = new GenericRowMapper<>(targetEntity, jdbcTemplate);
        Object relatedInstance = relatedMapper.mapRow(resultSet, resultSet.getRow());

        if (relatedInstance != null) {
            Method setter = type.getMethod("set" + capitalize(field.getName()), field.getType());
            setter.invoke(instance, relatedInstance);
        }
    }

    private void mapOneToMany(Object instance, Field field) throws Exception {
        OneToMany annotation = field.getAnnotation(OneToMany.class);
        Class<?> targetEntity = annotation.targetEntity();
        String mappedBy = annotation.mappedBy();

        String query = String.format("SELECT * FROM %s WHERE %s = ?", targetEntity.getSimpleName().toLowerCase(), mappedBy);
        executeQueryForRelatedEntities(instance, field, targetEntity, query);
    }

    private void mapManyToMany(Object instance, Field field) throws Exception {
        ManyToMany annotation = field.getAnnotation(ManyToMany.class);
        Class<?> targetEntity = annotation.targetEntity();
        String joinTable = annotation.joinTable();
        String joinColumn = annotation.joinColumn();
        String inverseJoinColumn = annotation.inverseJoinColumn();

        String query = String.format("SELECT t.* FROM %s t INNER JOIN %s jt ON t.id = jt.%s WHERE jt.%s = ?",
                getTableName(targetEntity), joinTable, inverseJoinColumn, joinColumn);


        executeQueryForRelatedEntities(instance, field, targetEntity, query);
    }

    private Object getPrimaryKey(Object instance) throws Exception {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                field.setAccessible(true);
                return field.get(instance);
            }
        }
        throw new MappingException("No primary key found in " + instance.getClass());
    }

    private String getTableName(Class<?> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null) {
            return tableAnnotation.name(); // Devuelve el nombre de la tabla desde la anotación
        }
        return entityClass.getSimpleName().toLowerCase(); // Por defecto usa el nombre de la clase
    }

    //@SuppressWarnings("deprecation")
    private void executeQueryForRelatedEntities(Object instance, Field field, Class<?> targetEntity, String query) throws Exception {
        List<?> relatedList = jdbcTemplate.query(query, new Object[]{getPrimaryKey(instance)},
                (rs, rowNum) -> new GenericRowMapper<>(targetEntity, jdbcTemplate).mapRow(rs, rowNum));

        Method setter = type.getMethod("set" + capitalize(field.getName()), field.getType());
        setter.invoke(instance, relatedList);
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
