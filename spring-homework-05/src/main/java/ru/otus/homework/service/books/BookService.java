package ru.otus.homework.service.books;

import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookService {
    void insertBook(long id, String title, long authorId, long genreId);
    void updateBookTitleById(long id, String title);
    Book getBookByTitle(String name);
    Book getBookById(long id);
    List<Book> getAllBooks();
    void deleteBookById(long id);
    void deleteBookByTitle(String name);
}
