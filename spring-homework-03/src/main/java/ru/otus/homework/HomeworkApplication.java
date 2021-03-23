package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.homework.service.survey.SurveyService;

import java.io.IOException;

@SpringBootApplication
public class HomeworkApplication{
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(HomeworkApplication.class, args);
        SurveyService surveyService = context.getBean(SurveyService.class);
        System.out.println(surveyService.takeASurvey());
    }
}
