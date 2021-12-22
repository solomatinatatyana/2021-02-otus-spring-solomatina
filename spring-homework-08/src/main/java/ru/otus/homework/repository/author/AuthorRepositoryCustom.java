package ru.otus.homework.repository.author;

import ru.otus.homework.domain.Author;

public interface AuthorRepositoryCustom {

    void createAuthor(Author author);
    void updateAuthorById(String id, Author author);
}
