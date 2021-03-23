package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Student")
public class StudentTest {

    @DisplayName("Класс Student корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectStudentConstructor(){
        Student student = new Student("firstName","secondName");
        assertAll("student",
                ()->assertEquals("firstName",student.getFirstName()),
                ()->assertEquals("secondName",student.getSecondName()));
    }
}
