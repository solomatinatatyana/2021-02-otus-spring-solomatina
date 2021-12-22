package ru.otus.homework.repository.genre;

import ru.otus.homework.domain.Genre;

public interface GenreRepositoryCustom {

    void createGenre(Genre genre);
    void updateGenreById(String id, Genre genre);
}
