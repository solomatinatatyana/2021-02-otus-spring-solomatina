package ru.otus.homework.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class LibraryBookServiceHealthIndicator implements HealthIndicator {

    private static final String SERVICE_MESSAGE_KEY = "Book Service";

    @Override
    public Health health() {
        if(isRunningBookService()){
            Health.up().withDetail(SERVICE_MESSAGE_KEY,"Not available").build();
        }
        return Health.up().withDetail(SERVICE_MESSAGE_KEY,"Available").build();
    }

    private boolean isRunningBookService(){
        return true;
    }
}
