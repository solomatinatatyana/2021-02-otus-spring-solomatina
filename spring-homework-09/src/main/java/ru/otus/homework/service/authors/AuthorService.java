package ru.otus.homework.service.authors;

import ru.otus.homework.domain.Author;
import ru.otus.homework.rest.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    void insertAuthor(Author author);
    void updateAuthorById(long id, Author author);
    Author getAuthorById(long id);
    Author getAuthorByName(String name);
    List<Author> getAllAuthors();
    void deleteAuthorById(long id);
}
