package ru.otus.homework.dao.author;

import ru.otus.homework.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void insert(Author author);
    Optional<Author> findById(long id);
    List<Author> findAll();
    void deleteById(long id);
}
