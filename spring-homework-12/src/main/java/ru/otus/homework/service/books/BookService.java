package ru.otus.homework.service.books;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;

import java.util.List;

public interface BookService {
    void createBook(Book book, Author author, Genre genre);
    void updateBookById(long id, Book book);
    Book getBookByTitle(String name);
    Book getBookById(long id);
    List<Book> getAllBooks();
    void deleteBookById(long id);
    void deleteBookByTitle(String name);

    List<BookComments> getBookCommentsCount();
    List<Book> getAllBooksWithGivenGenre(String genre);
    List<Book> getAllBooksWithGivenAuthor(String author);

    List<Comment> getCommentsByBookId(long bookId);

    long getBookCommentsCount(int bookId);

}
