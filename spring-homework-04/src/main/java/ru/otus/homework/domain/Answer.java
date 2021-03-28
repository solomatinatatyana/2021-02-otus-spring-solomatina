package ru.otus.homework.domain;

import lombok.Getter;

@Getter
public final class Answer {
    private final String answerText;

    public Answer(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerText='" + answerText + '\'' +
                '}';
    }
}
