package ru.otus.homework.service.authors;

import ru.otus.homework.domain.Author;

import java.util.List;

public interface AuthorService {
    void insertAuthor(Author author);
    void updateAuthorById(String id, Author author);
    Author getAuthorById(String id);
    Author getAuthorByName(String name);
    List<Author> getAllAuthors();
    void deleteAuthorById(String id);
}
