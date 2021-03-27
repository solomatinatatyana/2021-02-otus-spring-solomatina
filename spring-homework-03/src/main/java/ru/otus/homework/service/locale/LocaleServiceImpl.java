package ru.otus.homework.service.locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.utils.LocaleProvider;

import java.util.Locale;

@Service
public class LocaleServiceImpl implements LocaleService{

    private final MessageSource messageSource;
    private final LocaleProvider localeProvider;

    public LocaleServiceImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }


    @Override
    public String getLocalizationMessage(String property, Object ... args) {
        return messageSource.getMessage(property,args, Locale.forLanguageTag(localeProvider.getName()));
    }
}
