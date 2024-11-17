package com.fpmislata.basespring.persistence.dao.db.jdbc.mapper;

import com.fpmislata.basespring.common.annotation.persistence.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FieldCache {
    private static final Map<Class<?>, Map<String, Field>> CACHE = new ConcurrentHashMap<>();

    public static Map<String, Field> getCachedFields(Class<?> clazz) {
        return CACHE.computeIfAbsent(clazz, FieldCache::extractFields);
    }

    private static Map<String, Field> extractFields(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                field.setAccessible(true);
                fields.put(column.name(), field);
            }
        }
        return fields;
    }
}

