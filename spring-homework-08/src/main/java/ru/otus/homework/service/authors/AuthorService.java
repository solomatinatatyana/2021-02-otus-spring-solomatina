package ru.otus.homework.service.authors;

import ru.otus.homework.domain.Author;

import java.util.List;

public interface AuthorService {
    void insertAuthor(long id, String fio);
    Author getAuthorById(long id);
    List<Author> getAllAuthors();
    void deleteAuthorById(long id);
}
