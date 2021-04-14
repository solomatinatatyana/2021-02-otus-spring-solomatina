package ru.otus.homework.repository.author;

import ru.otus.homework.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryJpa {
    Author insert(Author author);
    Optional<Author> findById(long id);
    List<Author> findAll();
    void deleteById(long id);
}
