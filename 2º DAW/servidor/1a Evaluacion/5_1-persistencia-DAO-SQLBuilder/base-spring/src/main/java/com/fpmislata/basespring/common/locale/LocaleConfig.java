package com.fpmislata.basespring.common.locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LocaleConfig implements WebMvcConfigurer { // Configuracion del idioma de la aplicacion
    @Value("${app.language.default}")
    private String defaultLanguage; // idioma por defecto

    @Override
    public void addInterceptors(InterceptorRegistry registry) { // Añade el interceptor que se encarga de cambiar el idioma de la aplicacion
        registry.addInterceptor(new CustomLocaleChangeInterceptor(defaultLanguage));
    }
}
