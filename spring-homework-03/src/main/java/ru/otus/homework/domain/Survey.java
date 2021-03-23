package ru.otus.homework.domain;

import lombok.Data;

@Data
public class Survey {
    private String name;
    private String result;

    public Survey() { }

    public Survey(String name) {
        this.name = name;
    }
}
