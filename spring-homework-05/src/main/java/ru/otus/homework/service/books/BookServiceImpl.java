package ru.otus.homework.service.books;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.book.BookDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.BookException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void insertBook(long id, String title, long authorId, long genreId) {
        bookDao.insert(new Book(id, title, new Author(authorId), new Genre(genreId)));
    }

    @Override
    public void updateBookTitleById(long id, String title) {
        bookDao.updateTitleById(id, title);
    }

    @Override
    public Book getBookByTitle(String name) {
        return bookDao.findByName(name).orElseThrow(()-> new BookException("Book with name [" + name + "] not found"));
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.findById(id).orElseThrow(()-> new BookException("Book with id [" + id + "] not found"));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public void deleteBookByTitle(String name) {
        bookDao.deleteByName(name);
    }
}
