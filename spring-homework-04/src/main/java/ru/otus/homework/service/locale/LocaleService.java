package ru.otus.homework.service.locale;

public interface LocaleService {
    String getLocalizationMessage(String property, Object ... args);
}
