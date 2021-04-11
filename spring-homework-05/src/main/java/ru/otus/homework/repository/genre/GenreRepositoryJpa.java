package ru.otus.homework.repository.genre;

import ru.otus.homework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepositoryJpa {
    Genre insert(Genre genre);
    Optional<Genre> findById(long id);
    List<Genre> findAll();
    void deleteById(long id);
}
