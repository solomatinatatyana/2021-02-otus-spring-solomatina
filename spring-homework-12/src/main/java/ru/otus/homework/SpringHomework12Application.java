package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringHomework12Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringHomework12Application.class, args);
    }

}