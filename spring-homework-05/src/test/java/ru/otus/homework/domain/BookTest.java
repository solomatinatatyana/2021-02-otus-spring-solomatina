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
        Book book = new Book(1, "testTitleBook", 1, 1);
        assertAll("book",
                ()-> assertEquals(1, book.getId()),
                ()-> assertEquals("testTitleBook", book.getTitle()),
                ()-> assertEquals(1,book.getAuthorId()),
                ()-> assertEquals(1, book.getGenreId()));
    }
}
