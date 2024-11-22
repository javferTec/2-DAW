package com.fpmislata.basespring.persistence.dao.db.jdbc.utils.metadata;

import com.fpmislata.basespring.common.annotation.persistence.Column;
import com.fpmislata.basespring.common.annotation.persistence.PrimaryKey;
import com.fpmislata.basespring.common.annotation.persistence.Table;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.cache.ReflectionColumnFieldCache;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class EntityMetadataExtractor<T> {
    private final Class<T> entityClass;

    // Extrae los valores de las columnas de una entidad en un mapa clave-valor
    public Map<String, Object> extractColumnValues(Object entity) {
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
    public String getPrimaryKeyColumn(Class<?> entityClass) {
        return ReflectionColumnFieldCache.getCachedFields(entityClass).values().stream()
                .filter(field -> field.isAnnotationPresent(PrimaryKey.class)) // Filtra campos anotados como PrimaryKey
                .map(this::getColumnName) // Obtiene el nombre de la columna
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No primary key found for " + entityClass.getSimpleName())); // Lanza excepción si no encuentra clave primaria
    }

    // Sobrecarga que obtiene el nombre de la columna de la clave primaria de la entidad actual
    public String getPrimaryKeyColumn() {
        return getPrimaryKeyColumn(entityClass);
    }

    // Obtiene el nombre de la tabla correspondiente a una clase
    public String getTableName(Class<?> clazz) {
        return Optional.ofNullable(clazz.getAnnotation(Table.class))
                .map(Table::name) // Obtiene el nombre especificado en la anotación @Table
                .orElse(clazz.getSimpleName().toLowerCase()); // Retorna el nombre de la clase en minúsculas si no está anotada
    }

    // Sobrecarga que obtiene el nombre de la tabla de la entidad actual
    public String getTableName() {
        return getTableName(entityClass);
    }

    // Obtiene el nombre de la columna basado en su anotación o nombre del campo
    public String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null ? column.name() : field.getName(); // Retorna el nombre de la columna o el del campo
    }

    // Genera el SQL de selección de todos los registros para la tabla de la entidad actual
    public String getSelectSql() {
        return "SELECT * FROM " + getTableName(); // Retorna el SQL SELECT
    }

}
