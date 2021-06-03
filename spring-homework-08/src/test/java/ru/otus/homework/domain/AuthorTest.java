package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.homework.util.CommonUtils.getRandomRowId;

@DisplayName("Класс Author")
public class AuthorTest {
    @DisplayName("Автор корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectAuthorConstructor(){
        Author author = new Author(getRandomRowId(), "testAuthor");
        assertAll("author",
                ()-> assertEquals("testAuthor", author.getFullName()));
    }
}
