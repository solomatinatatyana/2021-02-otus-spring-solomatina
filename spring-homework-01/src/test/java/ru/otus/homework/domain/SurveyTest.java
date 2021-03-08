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

    @DisplayName("Проверка сеттера name класса Survey")
    @Test()
    public void shouldHaveCorrectNameSurveySetter(){
        Survey survey = new Survey();
        survey.setName("Survey1");
        assertEquals("Survey1", survey.getName());
    }

    @DisplayName("Проверка сеттера result класса Survey")
    @Test()
    public void shouldHaveCorrectResultSurveySetter(){
        Survey survey = new Survey();
        survey.setResult("Result");
        assertEquals("Result", survey.getResult());
    }
}
