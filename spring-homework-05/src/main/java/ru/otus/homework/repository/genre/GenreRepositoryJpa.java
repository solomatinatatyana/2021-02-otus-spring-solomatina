package ru.otus.homework.repository.genre;

import ru.otus.homework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepositoryJpa {
    Genre save(Genre genre);
    Optional<Genre> findById(long id);
    Optional<Genre> findByName(String genre);
    List<Genre> findAll();
    void deleteById(long id);
}
