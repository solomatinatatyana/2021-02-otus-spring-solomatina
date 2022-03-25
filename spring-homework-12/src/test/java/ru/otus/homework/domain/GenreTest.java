package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Genre")
public class GenreTest {
    @DisplayName("Жанр корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectGenreConstructor(){
        Genre genre = new Genre(1, "testGenre");
        assertAll("genre",
                ()-> assertEquals(1, genre.getId()),
                ()-> assertEquals("testGenre", genre.getName()));
    }
}
