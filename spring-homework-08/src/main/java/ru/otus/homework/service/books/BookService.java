package ru.otus.homework.service.books;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;

import java.util.List;

public interface BookService {
    void createBook(Book book, Author author, Genre genre);
    void updateBookById(String id, Book book);
    Book getBookByTitle(String name);
    Book getBookById(String id);
    List<Book> getAllBooks();
    void deleteBookById(String id);

    List<BookComments> groupBy(String bookId);

    long getBookCommentsCount(String bookId);
    List<Book> getAllBooksWithGivenGenre(String genre);
    List<Book> getAllBooksWithGivenAuthor(String author);
    double getAvgRatingComments(String id);
}
