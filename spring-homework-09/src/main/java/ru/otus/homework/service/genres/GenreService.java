package ru.otus.homework.service.genres;

import ru.otus.homework.domain.Genre;

import java.util.List;

public interface GenreService {
    void createGenre(Genre genre);
    void updateGenreById(String id, Genre genre);
    Genre getGenreById(String id);
    Genre getGenreByName(String name);
    List<Genre> getAllGenres();
    void deleteGenreById(String id);
}
