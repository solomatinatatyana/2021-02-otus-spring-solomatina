package ru.otus.homework.repository.book;

import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.dto.BookComments;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {
    Book insert(Book book);
    void updateTitleById(long id, String title);
    Optional<Book> findByName(String name);
    Optional<Book> findById(long id);
    List<Book> findAll();
    void deleteById(long id);
    void deleteByName(String name);

    List<BookComments> findBooksCommentsCount();
    List<Book> findAllBooksWithGivenGenre(String genre);
}
