package com.fpmislata.basespring.common.annotation.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ManyToMany {
    Class<?> targetEntity(); // La clase de la entidad relacionada
    String joinTable(); // Tabla intermedia
    String joinColumn(); // Columna de esta entidad en la tabla intermedia
    String inverseJoinColumn(); // Columna de la entidad relacionada en la tabla intermedia
}