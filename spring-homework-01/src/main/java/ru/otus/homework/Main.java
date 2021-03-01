package ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.domain.Survey;
import ru.otus.homework.service.SurveyService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-context.xml");

        Survey survey = ctx.getBean(Survey.class);
        SurveyService service = ctx.getBean(SurveyService.class);
        service.takeASurvey(service.getSurveyByName(survey.getName()));

        ctx.close();
    }




}
