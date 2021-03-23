package ru.otus.homework.domain;

public final class Student {
    private final String firstName;
    private final String secondName;

    public Student(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    @Override
    public String toString() {
        return "First name: " + getFirstName() + "\n" +
                "Second name: " + getSecondName();
    }
}
