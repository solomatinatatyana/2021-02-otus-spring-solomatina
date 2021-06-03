package ru.otus.homework.repository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.genre.GenreRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест класса GenreRepository должен ")
@DataJpaTest
public class GenreRepositoryTest {
    private static final long EXISTING_FIRST_GENRE_ID = 1L;
    private static final String EXISTING_FIRST_GENRE = "Fantasy";
    private static final long EXISTING_SECOND_GENRE_ID = 2L;
    private static final String EXISTING_SECOND_GENRE = "Roman";

    private static final long EXISTING_FIRST_BOOK_ID = 1L;
    private static final long EXISTING_SECOND_BOOK_ID = 2L;
    private static final long EXISTING_FIRST_AUTHOR_ID = 1L;
    private static final long EXISTING_SECOND_AUTHOR_ID = 2L;
    private static final String EXISTING_FIRST_BOOK = "The lord of the rings";
    private static final String EXISTING_SECOND_BOOK = "War and peace";
    private static final String EXISTING_FIRST_AUTHOR = "Tolkien";
    private static final String EXISTING_SECOND_AUTHOR = "Tolstoy";

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("проверять добавление нового жанра книги в БД")
    @Test
    public void shouldInsertNewGenre() {
        Genre expectedGenre = new Genre(3, "testGenre");
        genreRepository.saveAndFlush(expectedGenre);
        Genre actualGenre = genreRepository.findById(expectedGenre.getId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("проверять нахождение жанра книги по его id")
    @Test
    public void shouldReturnGenreById() {
        Genre expectedGenre = em.find(Genre.class, EXISTING_FIRST_GENRE_ID);
        Genre actualGenre = genreRepository.findById(expectedGenre.getId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("проверять нахождение всех жанров")
    @Test
    public void shouldReturnExpectedGenresList() {
        Genre g1 = new Genre(EXISTING_FIRST_GENRE_ID, EXISTING_FIRST_GENRE);
        Genre g2 = new Genre(EXISTING_SECOND_GENRE_ID, EXISTING_SECOND_GENRE);
        Author a1 = new Author(EXISTING_FIRST_AUTHOR_ID, EXISTING_FIRST_AUTHOR);
        Author a2 = new Author(EXISTING_SECOND_AUTHOR_ID, EXISTING_SECOND_AUTHOR);

        List<Genre> expectedGenreList = Arrays.asList(
                new Genre(EXISTING_FIRST_GENRE_ID, EXISTING_FIRST_GENRE,
                        Arrays.asList(new Book(EXISTING_FIRST_BOOK_ID, EXISTING_FIRST_BOOK,a1, g1))),
                new Genre(EXISTING_SECOND_GENRE_ID, EXISTING_SECOND_GENRE,
                        Arrays.asList(new Book(EXISTING_SECOND_AUTHOR_ID, EXISTING_SECOND_BOOK,a2, g2)))
        );
        List<Genre> actualGenreList = genreRepository.findAll();
        assertThat(CollectionUtils.isEqualCollection(actualGenreList, expectedGenreList));
    }

    @DisplayName("проверять удаление жанра по его id")
    @Test
    public void shouldDeleteCorrectGenreById() {
        Genre secondGenre = em.find(Genre.class, EXISTING_SECOND_GENRE_ID);
        assertThat(secondGenre).isNotNull();
        em.detach(secondGenre);

        genreRepository.deleteById(EXISTING_SECOND_GENRE_ID);
        Genre deletedGenre = em.find(Genre.class, EXISTING_SECOND_GENRE_ID);

        assertThat(deletedGenre).isNull();
    }
}
