package ru.otus.homework.service.books;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.dto.BookComments;
import ru.otus.homework.repository.book.BookRepositoryJpa;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.BookException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepositoryJpa bookRepositoryJpa;

    public BookServiceImpl(BookRepositoryJpa bookRepositoryJpa) {
        this.bookRepositoryJpa = bookRepositoryJpa;
    }

    @Transactional
    @Override
    public void insertBook(long id, String title, long authorId, long genreId) {
        bookRepositoryJpa.insert(new Book(id, title, new Author(authorId), new Genre(genreId)));
    }

    @Transactional
    @Override
    public void updateBookTitleById(long id, String title) {
        bookRepositoryJpa.updateTitleById(id, title);
    }

    @Override
    public Book getBookByTitle(String name) {
        return bookRepositoryJpa.findByName(name).orElseThrow(()-> new BookException("Book with name [" + name + "] not found"));
    }

    @Override
    public Book getBookById(long id) {
        return bookRepositoryJpa.findById(id).orElseThrow(()-> new BookException("Book with id [" + id + "] not found"));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepositoryJpa.findAll();
    }

    @Transactional
    @Override
    public void deleteBookById(long id) {
        bookRepositoryJpa.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteBookByTitle(String name) {
        bookRepositoryJpa.deleteByName(name);
    }

    @Override
    public List<BookComments> getBookCommentsCount() {
        return bookRepositoryJpa.findBooksCommentsCount();
    }

    @Override
    public List<Book> getAllBooksWithGivenGenre(String genre) {
        return bookRepositoryJpa.findAllBooksWithGivenGenre(genre);
    }
}
