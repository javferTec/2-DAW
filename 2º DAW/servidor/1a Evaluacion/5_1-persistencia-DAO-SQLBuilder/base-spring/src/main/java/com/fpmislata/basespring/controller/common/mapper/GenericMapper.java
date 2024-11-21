package com.fpmislata.basespring.controller.common.mapper;

import com.fpmislata.basespring.common.annotation.controller.MappedField;
import com.fpmislata.basespring.common.annotation.persistence.Mapper;

import java.lang.reflect.Field;

@Mapper
public class GenericMapper {

    public static <D, C> C mapToControllerModel(D domainObject, Class<C> controllerClass) throws IllegalAccessException, InstantiationException {
        // Crear una nueva instancia del modelo de controlador
        C controllerObject = controllerClass.newInstance();

        // Obtenemos todos los campos de la clase del controlador
        Field[] controllerFields = controllerClass.getDeclaredFields();

        for (Field field : controllerFields) {
            field.setAccessible(true);  // Para acceder a campos privados

            // Intentamos obtener el nombre del campo en la entidad de dominio (si coincide o no con el nombre del controlador)
            String domainFieldName = field.getName();

            // Si el campo tiene la anotación @MappedField, usamos el nombre del campo especificado en la anotación
            if (field.isAnnotationPresent(MappedField.class)) {
                domainFieldName = field.getAnnotation(MappedField.class).value();
            }

            // Buscamos el campo correspondiente en la entidad de dominio
            try {
                Field domainField = domainObject.getClass().getDeclaredField(domainFieldName);
                domainField.setAccessible(true); // Accedemos al campo de la entidad de dominio

                // Mapeamos el valor del campo de la entidad de dominio al controlador
                field.set(controllerObject, domainField.get(domainObject));
            } catch (NoSuchFieldException e) {
                // Si no se encuentra el campo, lo ignoramos o lo manejamos de otra manera
                System.out.println("Campo no encontrado: " + domainFieldName);
            }
        }

        return controllerObject;
    }
}
