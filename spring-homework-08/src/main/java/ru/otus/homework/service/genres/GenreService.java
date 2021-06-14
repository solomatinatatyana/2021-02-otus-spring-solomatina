package ru.otus.homework.service.genres;

import ru.otus.homework.domain.Genre;

import java.util.List;

public interface GenreService {
    void insertGenre(long id, String name);
    Genre getGenreById(long id);
    List<Genre> getAllGenres();
    void deleteGenreById(long id);
}
