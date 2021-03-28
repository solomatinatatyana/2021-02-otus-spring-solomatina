package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Question")
public class QuestionTest {

    @DisplayName("Проверка сеттера класса Question")
    @Test()
    public void shouldHaveCorrectQuestionSetter(){
        Question question = new Question();
        question.setQuestionText("Question1");
        assertEquals("Question1", question.getQuestionText());
    }
}
