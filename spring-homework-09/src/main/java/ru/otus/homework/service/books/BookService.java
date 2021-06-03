package ru.otus.homework.service.books;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

public interface BookService {
    void createBook(Book book, Author author, Genre genre);
    void updateBookById(String id, Book book);
    void deleteBookById(String id);

    Book getBookByTitle(String name);
    Book getBookById(String id);

    List<Book> getAllBooks();
    List<Book> getAllBooksWithGivenGenre(String genre);
    List<Book> getAllBooksWithGivenAuthor(String author);
    List<Comment> getCommentsByBookId(String bookId);

    long getBookCommentsCount(String bookId);
    double getAvgRatingComments(String id);
}
