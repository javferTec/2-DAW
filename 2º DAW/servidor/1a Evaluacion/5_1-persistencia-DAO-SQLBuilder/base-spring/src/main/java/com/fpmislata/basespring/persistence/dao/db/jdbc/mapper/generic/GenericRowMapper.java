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

    // Se crea un logger para la clase se utiliza para registrar mensajes de log en diferentes niveles de severidad (info, debug, error, etc.)
    private static final Logger logger = LoggerFactory.getLogger(GenericRowMapper.class);

    private final Class<T> type; // Clase del objeto a mapear
    private final JdbcTemplate jdbcTemplate;

    // Metodo que mapea una fila de la base de datos a un objeto de tipo T (Generico)
    @Override
    public T mapRow(@NonNull ResultSet resultSet, int rowNum) {
        try {
            // Se crea una nueva instancia del objeto T
            T instance = type.getDeclaredConstructor().newInstance();
            // Se obtienen los campos de la clase utilizando un cache
            Map<String, Field> fields = FieldCache.getCachedFields(type);

            // Mapea los campos simples
            for (Map.Entry<String, Field> entry : fields.entrySet()) {
                // Se obtiene el nombre de la columna
                String columnName = getColumnName(entry.getValue());
                // Se asigna el valor al campo del objeto
                setFieldValue(instance, resultSet, entry.getValue(), columnName);
            }

            // Procesa las relaciones (OneToOne, OneToMany, ManyToMany)
            processRelationships(resultSet, instance);

            return instance;
        } catch (Exception e) {
            // Se captura cualquier excepcion y se loguea
            logger.error("Error mapping row to {}: {}", type.getSimpleName(), e.getMessage(), e);
            throw new MappingException("Error mapping row to " + type.getSimpleName(), e);
        }
    }

    // Metodo para obtener el nombre de la columna a partir de la anotacion Column
    private String getColumnName(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        return columnAnnotation != null ? columnAnnotation.name() : field.getName(); // Si tiene la anotacion Column, usa el nombre definido
    }

    // Metodo para establecer el valor de un campo en el objeto
    private void setFieldValue(T instance, ResultSet resultSet, Field field, String columnName) throws Exception {
        String setterName = "set" + capitalize(field.getName()); // Crea el nombre del setter
        Method setter = type.getMethod(setterName, field.getType()); // Obtiene el metodo setter

        // Verifica si la columna existe en el resultado
        if (columnExists(resultSet, columnName)) {
            // Si la columna existe, obtiene el valor y lo establece en el campo
            Object value = resultSet.getObject(columnName, field.getType());
            logger.debug("Setting field '{}' with value '{}' from column '{}'", field.getName(), value, columnName);
            setter.invoke(instance, value); // Llama al setter
        } else {
            // Si la columna no existe, se muestra una advertencia
            logger.warn("Column '{}' does not exist in result set for field '{}' of entity {}", columnName, field.getName(), type.getSimpleName());
        }
    }

    // Metodo para verificar si una columna existe en el ResultSet
    private boolean columnExists(ResultSet resultSet, String columnName) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData(); // Obtiene los metadatos del resultado
            int columnCount = metaData.getColumnCount(); // Obtiene el numero de columnas
            // Itera sobre todas las columnas para verificar si existe
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

    // Metodo para procesar las relaciones (OneToOne, OneToMany, ManyToMany)
    private void processRelationships(ResultSet resultSet, Object instance) throws Exception {
        // Itera sobre los campos de la clase
        for (Field field : type.getDeclaredFields()) {
            // Si tiene la anotacion OneToOne, procesa la relacion
            if (field.isAnnotationPresent(OneToOne.class)) {
                try {
                    mapOneToOne(resultSet, instance, field);
                } catch (Exception e) {
                    logger.warn("Error mapping OneToOne relationship for field '{}': {}", field.getName(), e.getMessage());
                }
            } else if (field.isAnnotationPresent(OneToMany.class)) {
                // Si tiene la anotacion OneToMany, procesa la relacion
                mapOneToMany(instance, field);
            } else if (field.isAnnotationPresent(ManyToMany.class)) {
                // Si tiene la anotacion ManyToMany, procesa la relacion
                mapManyToMany(instance, field);
            }
        }
    }

    // Metodo para mapear una relacion OneToOne
    private void mapOneToOne(ResultSet resultSet, Object instance, Field field) throws Exception {
        OneToOne annotation = field.getAnnotation(OneToOne.class);
        String joinColumn = annotation.joinColumn(); // Obtiene el nombre de la columna FK (clave foranea)
        Class<?> targetEntity = annotation.targetEntity(); // Obtiene la clase de la entidad relacionada

        // Obtiene el valor de la clave foranea desde el ResultSet
        Object foreignKeyValue = resultSet.getObject(joinColumn);
        if (foreignKeyValue != null) {
            String query = String.format("SELECT * FROM %s WHERE id = ?", getTableName(targetEntity));
            // Realiza una consulta para obtener la entidad relacionada
            Object relatedInstance = jdbcTemplate.queryForObject(query, new Object[]{foreignKeyValue}, new GenericRowMapper<>(targetEntity, jdbcTemplate));
            setRelatedField(instance, field, relatedInstance); // Establece el valor de la entidad relacionada
        }
    }

    // Metodo para mapear una relacion OneToMany
    private void mapOneToMany(Object instance, Field field) throws Exception {
        OneToMany annotation = field.getAnnotation(OneToMany.class);

        // Verifica que el campo sea una coleccion
        if (!Collection.class.isAssignableFrom(field.getType())) {
            throw new MappingException("Field " + field.getName() + " must be a Collection for @OneToMany");
        }

        // Mapea las entidades relacionadas
        List<?> relatedList = mapRelatedEntities(annotation.targetEntity(), buildOneToManyQuery(annotation), instance);

        setRelatedField(instance, field, relatedList); // Establece la lista relacionada en el campo
    }

    // Metodo para mapear una relacion ManyToMany
    private void mapManyToMany(Object instance, Field field) throws Exception {
        ManyToMany annotation = field.getAnnotation(ManyToMany.class);
        List<?> relatedList = mapRelatedEntities(annotation.targetEntity(), buildManyToManyQuery(annotation), instance);

        setRelatedField(instance, field, relatedList); // Establece la lista relacionada en el campo
    }

    // Metodo para mapear entidades relacionadas
    private List<?> mapRelatedEntities(Class<?> targetEntity, String query, Object instance) {
        try {
            // Realiza la consulta y mapea las entidades relacionadas
            return jdbcTemplate.query(query, new Object[]{getPrimaryKey(instance)},
                    (rs, rowNum) -> new GenericRowMapper<>(targetEntity, jdbcTemplate).mapRow(rs, rowNum));
        } catch (Exception e) {
            logger.error("Error mapping related entities: {}", e.getMessage(), e);
            return Collections.emptyList(); // Devuelve una lista vacia en caso de error
        }
    }

    // Metodo para construir la consulta de una relacion OneToMany
    private String buildOneToManyQuery(OneToMany annotation) {
        return String.format("SELECT * FROM %s WHERE %s = ?",
                annotation.targetEntity().getSimpleName().toLowerCase(), annotation.mappedBy());
    }

    // Metodo para construir la consulta de una relacion ManyToMany
    private String buildManyToManyQuery(ManyToMany annotation) {
        return String.format("SELECT t.* FROM %s t INNER JOIN %s jt ON t.id = jt.%s WHERE jt.%s = ?",
                getTableName(annotation.targetEntity()), annotation.joinTable(), annotation.inverseJoinColumn(), annotation.joinColumn());
    }

    // Metodo para establecer el valor de una entidad relacionada
    private void setRelatedField(Object instance, Field field, Object value) throws Exception {
        Method setter = type.getMethod("set" + capitalize(field.getName()), field.getType());
        setter.invoke(instance, value); // Llama al setter para establecer el valor
    }

    // Metodo para obtener la clave primaria de una entidad
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

    // Metodo para obtener el campo de la clave primaria de una entidad
    private Optional<Field> getPrimaryKeyField(Object instance) {
        return Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(PrimaryKey.class)) // Filtra los campos con la anotacion PrimaryKey
                .findFirst(); // Devuelve el primer campo que tenga la anotacion
    }

    // Metodo para obtener el nombre de la tabla a partir de la anotacion Table
    private String getTableName(Class<?> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        return tableAnnotation != null ? tableAnnotation.name() : entityClass.getSimpleName().toLowerCase();
    }

    // Metodo para capitalizar la primera letra de una cadena
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
