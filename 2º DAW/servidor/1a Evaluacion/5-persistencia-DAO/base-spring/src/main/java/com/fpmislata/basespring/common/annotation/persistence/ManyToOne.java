package com.fpmislata.basespring.common.annotation.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {
    String joinColumn(); // columna de unión en la tabla principal
    Class<Object> targetEntity();
}
