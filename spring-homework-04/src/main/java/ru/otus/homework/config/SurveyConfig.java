package ru.otus.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "survey")
@Component
public class SurveyConfig {
    private int countCorrectAnswers;

    public int getCountCorrectAnswers() {
        return countCorrectAnswers;
    }

    public void setCountCorrectAnswers(int countCorrectAnswers) {
        this.countCorrectAnswers = countCorrectAnswers;
    }
}
