package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.genre.GenreRepository;
import ru.otus.homework.util.RawResultPrinterImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.homework.repository"})
@Import(RawResultPrinterImpl.class)
@DisplayName("Тест класса GenreRepository должен ")
public class GenreRepositoryTest {
    private static final String EXISTING_FIRST_GENRE = "Фантастика";
    private static final String EXISTING_SECOND_GENRE = "Роман";

    @Autowired
    private GenreRepository genreRepository;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("проверять добавление нового жанра книги в БД")
    @Test
    public void shouldInsertNewGenre() {
        Genre expectedGenre = new Genre("testGenre");
        genreRepository.save(expectedGenre);
        assertThat(expectedGenre.getId()).isNotBlank();
        Genre actualGenre = genreRepository.findById(expectedGenre.getId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("проверять нахождение жанра книги по его id")
    @Test
    public void shouldReturnGenreById() {
        List<Genre> genres = genreRepository.findAll();
        Genre expectedGenre = genres.get(0);
        Genre actualGenre = genreRepository.findById(expectedGenre.getId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("проверять нахождение всех жанров")
    @Test
    public void shouldReturnExpectedGenresList() {
        List<String> expectedGenreNameList = Arrays.asList(
                new Genre(EXISTING_FIRST_GENRE).getName(),
                new Genre(EXISTING_SECOND_GENRE).getName()
        );
        List<Genre> actualGenreList = genreRepository.findAll();
        List<String> actualGenreNameList = new ArrayList<>();
        actualGenreList.forEach(genre -> actualGenreNameList.add(genre.getName()));
        assertThat(actualGenreNameList).containsAll(expectedGenreNameList);
    }

    @DisplayName("проверять удаление жанра по его id")
    @Test
    public void shouldDeleteCorrectGenreById() {
        List<Genre> genresBeforeDeleting = genreRepository.findAll();
        int countBeforeDeleting = genresBeforeDeleting.size();

        Genre deletingGenre = genresBeforeDeleting.get(0);
        assertThat(deletingGenre).isNotNull();

        genreRepository.deleteById(deletingGenre.getId());
        List<Genre> genresAfterDeleting = genreRepository.findAll();
        int countAfterDeleting = genresAfterDeleting.size();

        assertThat(countBeforeDeleting).isNotEqualTo(countAfterDeleting);
        assertThat(deletingGenre).isNotIn(genresAfterDeleting);
    }
}
