package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Answer")
public class AnswerTest {

    @DisplayName(" корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectAnswerSetter(){
        Answer answer = new Answer("Answer1");
        assertEquals("Answer1", answer.getAnswerText());
    }
}
