package ru.otus.homework.service.books;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.repository.book.BookRepositoryJpa;
import ru.otus.homework.repository.genre.GenreRepositoryJpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepositoryJpa bookRepositoryJpa;
    private final GenreRepositoryJpa genreRepositoryJpa;

    public BookServiceImpl(BookRepositoryJpa bookRepositoryJpa, GenreRepositoryJpa genreRepositoryJpa) {
        this.bookRepositoryJpa = bookRepositoryJpa;
        this.genreRepositoryJpa = genreRepositoryJpa;
    }

    @Transactional
    @Override
    public void insertBook(long id, String title, long authorId, long genreId) {
        bookRepositoryJpa.save(new Book(id, title, new Author(authorId), new Genre(genreId)));
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
        Genre g =  genreRepositoryJpa.findByName(genre).orElseThrow(()->new BookException("Нет такого жанра!"));
        List<Book> bookList = bookRepositoryJpa.findAll();
        return bookList.stream().filter(book -> book.getGenre().equals(g)).collect(Collectors.toList());
    }
}
