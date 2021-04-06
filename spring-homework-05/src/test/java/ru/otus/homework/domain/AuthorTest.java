package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Author")
public class AuthorTest {
    @DisplayName("Автор корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectAuthorConstructor(){
        Author author = new Author(1, "testAuthor");
        assertAll("author",
                ()-> assertEquals(1, author.getId()),
                ()-> assertEquals("testAuthor", author.getFullName()));
    }
}
