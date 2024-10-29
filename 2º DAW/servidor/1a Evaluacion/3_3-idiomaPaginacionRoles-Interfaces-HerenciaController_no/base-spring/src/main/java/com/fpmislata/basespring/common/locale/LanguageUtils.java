package com.fpmislata.basespring.common.locale;

import java.util.Locale;

public class LanguageUtils {
    private static final ThreadLocal<Locale> currentLocale = new ThreadLocal<>(); // Es para que cada hilo tenga su propia instancia de Locale (hilo = conexion)

    public static void setCurrentLocale(Locale locale) { // Establece el idioma actual
        currentLocale.set(locale);
    }

    public static String getCurrentLanguage() { // Obtiene el idioma actual
        Locale locale = currentLocale.get();
        return locale != null ? locale.getLanguage() : Locale.getDefault().getLanguage();
    }
}
