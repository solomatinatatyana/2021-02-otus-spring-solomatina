package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.dao.genre.GenreDao;
import ru.otus.homework.dao.genre.GenreDaoJdbc;
import ru.otus.homework.domain.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тест класса GenreDaoJdbc должен ")
@JdbcTest
@Import(GenreDaoJdbc.class)
public class GenreDaoJdbcTest {
    private static final int EXISTING_FIRST_GENRE_ID = 1;
    private static final String EXISTING_FIRST_GENRE = "Fantasy";
    private static final int EXISTING_SECOND_GENRE_ID = 2;
    private static final String EXISTING_SECOND_GENRE = "Roman";


    @Autowired
    private GenreDao genreDao;

    @DisplayName("проверять добавление нового жанра книги в БД")
    @Test
    public void shouldInsertNewGenre() {
        Genre expectedGenre = new Genre(3, "testGenre");
        genreDao.insert(expectedGenre);
        Genre actualGenre = genreDao.findById(expectedGenre.getId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("проверять нахождение жанра книги по его id")
    @Test
    public void shouldReturnGenreById() {
        Genre expectedGenre = new Genre(EXISTING_FIRST_GENRE_ID, EXISTING_FIRST_GENRE);
        Genre actualGenre = genreDao.findById(expectedGenre.getId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("проверять нахождение всех жанров")
    @Test
    public void shouldReturnExpectedGenresList() {
        List<Genre> expectedGenreList = Arrays.asList(
                new Genre(EXISTING_FIRST_GENRE_ID, EXISTING_FIRST_GENRE),
                new Genre(EXISTING_SECOND_GENRE_ID, EXISTING_SECOND_GENRE)
        );
        List<Genre> actualGenreList = genreDao.findAll();
        assertThat(actualGenreList).containsAll(expectedGenreList);
    }

    @DisplayName("проверять удаление жанра по его id")
    @Test
    public void shouldDeleteCorrectGenreById() {
        assertThatCode(() -> genreDao.findById(EXISTING_SECOND_GENRE_ID))
                .doesNotThrowAnyException();

        genreDao.deleteById(EXISTING_SECOND_GENRE_ID);

        assertThatThrownBy(() -> genreDao.findById(EXISTING_SECOND_GENRE_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
