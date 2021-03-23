package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:properties/application.properties")
@Configuration
public class Config {

    @Value("${survey.name}")
    private String surveyName;
    @Value("${survey.correct.answers.name}")
    private String correctAnswersFileName;
    @Value("${survey.number.correct.answers}")
    private String countCorrectAnswers;

    public String getSurveyName() {
        return surveyName;
    }

    public String getCorrectAnswersFileName() {
        return correctAnswersFileName;
    }

    public int getCountCorrectAnswers() {
        return Integer.parseInt(countCorrectAnswers);
    }
}
