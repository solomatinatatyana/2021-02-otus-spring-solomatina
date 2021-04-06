package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.dao.genre.GenreDao;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.service.genres.GenreServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@TestPropertySource("classpath:application.yml")
@DisplayName("Сервис GenreService должен ")
@SpringBootTest
public class GenreServiceImplTest {

    @MockBean
    private GenreDao genreDao;
    @Autowired
    private GenreServiceImpl genreService;


    @DisplayName("получать жанр книги по его id")
    @Test
    public void shouldReturnGenreById(){
        given(genreDao.findById(1)).willReturn(Optional.of(new Genre(1,"test")));
        Genre actualGenre = genreService.getGenreById(1);
        assertThat(actualGenre).isNotNull();
    }

    @DisplayName("получать все жанры")
    @Test
    public void shouldReturnAllGenres(){
        List<Genre> expectedGenreList = Arrays.asList(
                new Genre(1,"testGenre1"),
                new Genre(2,"testGenre2"));
        given(genreDao.findAll()).willReturn(expectedGenreList);
        List<Genre> actualGenreList = genreService.getAllGenres();
        assertThat(actualGenreList.equals(expectedGenreList));
    }

    @DisplayName("получать ошибку при запросе на несуществующий жанр")
    @Test
    public void shouldThrowGenreException(){
        Throwable exception = assertThrows(GenreException.class,()->{
            given(genreDao.findById(2)).willReturn(Optional.empty());
            genreService.getGenreById(2);
        });
        assertEquals("Genre with id [2] not found",exception.getMessage());
    }
}
