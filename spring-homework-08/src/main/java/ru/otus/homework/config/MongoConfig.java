package ru.otus.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.events.MongoBookCascadeSaveEventsListener;

@Configuration
public class MongoConfig {
    @Bean
    public MongoBookCascadeSaveEventsListener mongoBookCascadeSaveEventsListener() {
        return new MongoBookCascadeSaveEventsListener();
    }
}
