package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Survey")
public class SurveyTest {

    @DisplayName("Опрос корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectSurveyConstructor(){
        Survey survey = new Survey("test.csv");
        assertEquals("test.csv", survey.getName());
    }

    @DisplayName("Проверка сеттера класса Survey")
    @Test()
    public void shouldHaveCorrectSurveySetter(){
        Survey survey = new Survey();
        survey.setName("Survey1");
        assertEquals("Survey1", survey.getName());
    }


}
