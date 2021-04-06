package ru.otus.homework.dao.genre;

import ru.otus.homework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    void insert(Genre genre);
    Optional<Genre> findById(long id);
    List<Genre> findAll();
    void deleteById(long id);
}
