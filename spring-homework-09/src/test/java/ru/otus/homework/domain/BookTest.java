package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Book")
public class BookTest {

    @DisplayName("Книга корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectBookConstructor(){
        Book book = new Book("testTitleBook",
                new Author("testAuthor"),
                new Genre("testGenre"));
        assertAll("book",
                ()-> assertEquals("testTitleBook", book.getTitle()),
                ()-> assertEquals("testAuthor", book.getAuthor().getFullName()),
                ()-> assertEquals("testGenre", book.getGenre().getName()));
    }
}
