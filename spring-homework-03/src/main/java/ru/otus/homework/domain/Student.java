package ru.otus.homework.domain;

import lombok.Getter;

@Getter
public final class Student {
    private final String firstName;
    private final String secondName;

    public Student(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return "First name: " + getFirstName() + "\n" +
                "Second name: " + getSecondName();
    }
}
