package ru.otus.homework.service.genres;

import ru.otus.homework.domain.Genre;

import java.util.List;

public interface GenreService {
    void insertGenre(Genre genre);
    void updateGenreById(long id, Genre genre);
    Genre getGenreById(long id);
    Genre getGenreByName(String name);
    List<Genre> getAllGenres();
    void deleteGenreById(long id);
}
