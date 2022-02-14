package ru.otus.homework.service.books;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.repository.author.AuthorRepository;
import ru.otus.homework.repository.book.BookRepository;
import ru.otus.homework.repository.genre.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
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
        if (!bookRepository.existsBookByTitle(book.getTitle())) {
            bookRepository.save(book);
        } else {
            throw new BookException("book with title [" + book.getTitle() + "] is already exist!");
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
        return bookRepository.findByTitle(title).orElseThrow(() -> new BookException("Book with id [" + title + "] not found"));
    }

    @Override
    public Book getBookById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookException("Book with id [" + id + "] not found"));
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
    public long getBookCommentsCount(String bookId) {
        return getCommentsByBookId(bookId).size();
    }

    @Override
    public List<Book> getAllBooksWithGivenGenre(String genre) {
        Genre foundGenre = genreRepository.findByName(genre).orElseThrow(() -> new AuthorException("Genre with name [" + genre + "] not found"));
        return bookRepository.findAll().stream()
                .filter(book -> book.getGenre().equals(foundGenre))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBooksWithGivenAuthor(String author) {
        Author foundAuthor = authorRepository.findByFullName(author).orElseThrow(() -> new AuthorException("Author with fio [" + author + "] not found"));
        return bookRepository.findAll().stream()
                .filter(book -> book.getAuthor().equals(foundAuthor))
                .collect(Collectors.toList());
    }

    @Override
    public double getAvgRatingComments(String bookId) {
        return getCommentsByBookId(bookId)
                .stream()
                .mapToDouble(Comment::getRating)
                .average().getAsDouble();
    }

    /**
     * Получить все комментарии по книге
     * @param bookId id книги
     * @return List<Comment>
     */
    @Override
    public List<Comment> getCommentsByBookId(String bookId){
         return bookRepository.findAll().stream()
                .filter(b -> b.getId().equals(bookId))
                .map(Book::getComments)
                .collect(Collectors.toList()).get(0);
    }
}
