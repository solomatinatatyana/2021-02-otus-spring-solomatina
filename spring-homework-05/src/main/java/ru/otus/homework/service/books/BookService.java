package ru.otus.homework.service.books;

import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookComments;

import java.util.List;

public interface BookService {
    void insertBook(long id, String tittle, long authorId, long genreId);
    Book getBookByTitle(String name);
    Book getBookById(long id);
    List<Book> getAllBooks();
    void deleteBookById(long id);
    void deleteBookByTitle(String name);

    List<BookComments> getBookCommentsCount();
    List<Book> getAllBooksWithGivenGenre(String genre);
}
