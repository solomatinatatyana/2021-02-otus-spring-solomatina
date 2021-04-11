package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.genre.GenreRepositoryJpa;
import ru.otus.homework.repository.genre.GenreRepositoryJpaImpl;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест класса GenreRepositoryJpaImpl должен ")
@DataJpaTest
@Import(GenreRepositoryJpaImpl.class)
public class GenreRepositoryJpaImplTest {
    private static final long EXISTING_FIRST_GENRE_ID = 1L;
    private static final String EXISTING_FIRST_GENRE = "Fantasy";
    private static final long EXISTING_SECOND_GENRE_ID = 2L;
    private static final String EXISTING_SECOND_GENRE = "Roman";

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("проверять добавление нового жанра книги в БД")
    @Test
    public void shouldInsertNewGenre() {
        Genre expectedGenre = new Genre(3, "testGenre");
        genreRepositoryJpa.insert(expectedGenre);
        Genre actualGenre = genreRepositoryJpa.findById(expectedGenre.getId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("проверять нахождение жанра книги по его id")
    @Test
    public void shouldReturnGenreById() {
        Genre expectedGenre = em.find(Genre.class, EXISTING_FIRST_GENRE_ID);
        Genre actualGenre = genreRepositoryJpa.findById(expectedGenre.getId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("проверять нахождение всех жанров")
    @Test
    public void shouldReturnExpectedGenresList() {
        List<Genre> expectedGenreList = Arrays.asList(
                new Genre(EXISTING_FIRST_GENRE_ID, EXISTING_FIRST_GENRE),
                new Genre(EXISTING_SECOND_GENRE_ID, EXISTING_SECOND_GENRE)
        );
        List<Genre> actualGenreList = genreRepositoryJpa.findAll();
        assertThat(actualGenreList).containsAll(expectedGenreList);
    }

    @DisplayName("проверять удаление жанра по его id")
    @Test
    public void shouldDeleteCorrectGenreById() {
        Genre secondGenre = em.find(Genre.class, EXISTING_SECOND_GENRE_ID);
        assertThat(secondGenre).isNotNull();
        em.detach(secondGenre);

        genreRepositoryJpa.deleteById(EXISTING_SECOND_GENRE_ID);
        Genre deletedGenre = em.find(Genre.class, EXISTING_SECOND_GENRE_ID);

        assertThat(deletedGenre).isNull();
    }
}
