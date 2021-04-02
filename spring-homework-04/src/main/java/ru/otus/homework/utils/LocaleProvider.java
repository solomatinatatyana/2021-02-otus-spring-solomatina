package ru.otus.homework.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ConfigurationProperties(prefix = "locale")
@Component
public class LocaleProvider {

    private String name;
    private Locale locale;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocale(String name){
        this.locale = Locale.forLanguageTag(name);
    }

    public Locale getLocale() {
        return locale;
    }
}
