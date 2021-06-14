package ru.otus.homework.service.books;

import org.springframework.data.repository.query.Param;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;

import java.util.List;

public interface BookService {
    Book createBook(long id, String title, long author, long genre);
    Book getBookByTitle(String name);
    Book getBookById(long id);
    List<Book> getAllBooks();
    void deleteBookById(long id);
    void deleteBookByTitle(String name);

    //List<BookComments> getBookCommentsCount();
    List<Book> getAllBooksWithGivenGenre(String genre);
    List<Book> getAllBooksWithGivenAuthor(String author);
    List<Book> getAllBooksNotLikeGenre(String genre);

    //List<BookComments> getAllBooksByCountCommentsGreaterOrEqualsThan(long count);

}
