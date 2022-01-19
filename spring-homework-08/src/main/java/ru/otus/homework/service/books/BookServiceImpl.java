package ru.otus.homework.service.books;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.repository.author.AuthorRepository;
import ru.otus.homework.repository.book.BookRepository;
import ru.otus.homework.repository.genre.GenreRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, GenreRepository genreRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Override
    public void createBook(Book book, Author author, Genre genre) {
        book.setAuthor(author);
        book.setGenre(genre);
        if(!bookRepository.existsBookByTitle(book.getTitle())){
            bookRepository.save(book);
        }else {
            throw new BookException("book with title ["+ book.getTitle() +"] is already exist!");
        }
    }

    @Override
    public void updateBookById(String id, Book book) {
        Book bookToBeUpdated = getBookById(id);
        bookToBeUpdated.setTitle(book.getTitle());
        bookToBeUpdated.setGenre(genreRepository.findByName(book.getGenre().getName()).get());
        bookToBeUpdated.setAuthor(authorRepository.findByFullName(book.getAuthor().getFullName()).get());
        bookRepository.save(bookToBeUpdated);
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(()-> new BookException("Book with id [" + title + "] not found"));
    }

    @Override
    public Book getBookById(String id) {
        return bookRepository.findById(id).orElseThrow(()-> new BookException("Book with id [" + id + "] not found"));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteBookById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookComments> groupBy(String bookId) {
        return bookRepository.groupBy(bookId);
    }

    @Override
    public long getBookCommentsCount(String bookId) {
        return bookRepository.getBookCommentsByBookId(bookId).get(0).getCommentsCount();
    }

    @Override
    public List<Book> getAllBooksWithGivenGenre(String genre) {
        return bookRepository.findAllByGenre_Name(genre);
    }

    @Override
    public List<Book> getAllBooksWithGivenAuthor(String author) {
        return bookRepository.findAllByAuthor_FullName(author);
    }

    @Override
    public double getAvgRatingComments(String bookId) {
        return bookRepository.getBookCommentsByBookId(bookId).get(0).getAvgCommentsRating();
    }
}
