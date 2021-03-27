package ru.otus.homework.domain;

import lombok.Data;

@Data
public class Answer {
    private String answerText;

    public Answer() { }

    @Override
    public String toString() {
        return "Answer{" +
                "answerText='" + answerText + '\'' +
                '}';
    }
}
