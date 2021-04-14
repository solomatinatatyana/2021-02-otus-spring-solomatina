package ru.otus.homework.dao.book;

import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void insert(Book book);
    void updateTitleById(long id, String title);
    Optional<Book> findByName(String name);
    Optional<Book> findById(long id);
    List<Book> findAll();
    void deleteById(long id);
    void deleteByName(String name);
}
