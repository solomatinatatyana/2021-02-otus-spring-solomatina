package ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.otus.homework.service.SurveyService;

import java.io.IOException;

@EnableAspectJAutoProxy
@Configuration
@ComponentScan
public class Main {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);

        SurveyService service = ctx.getBean(SurveyService.class);
        String result = service.takeASurvey();
        System.out.println(result);
    }
}
