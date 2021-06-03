package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;
import ru.otus.homework.repository.author.AuthorRepository;
import ru.otus.homework.repository.book.BookRepository;
import ru.otus.homework.repository.genre.GenreRepository;
import ru.otus.homework.util.RawResultPrinterImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.homework.repository"})
@Import(RawResultPrinterImpl.class)
@DisplayName("Тест класса BookRepository должен ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;


    private static final String EXISTING_FIRST_GENRE = "Фантастика";
    private static final String EXISTING_SECOND_GENRE = "Роман";
    private static final String EXISTING_FIRST_BOOK = "Властелин колец";
    private static final String EXISTING_SECOND_BOOK = "Война и мир";
    private static final String EXISTING_FIRST_AUTHOR = "Джон Толкин";
    private static final String EXISTING_SECOND_AUTHOR = "Лев Толстой";

    private static final int BOOK_COUNT = 1;
    private static final int FIRST_BOOK_COMMENTS_COUNT = 2;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("проверять добавление новой книги в БД")
    @Test
    public void shouldInsertNewBook() {
        Author author = authorRepository.findByFullName(EXISTING_SECOND_AUTHOR).get();
        Genre genre = genreRepository.findByName(EXISTING_SECOND_GENRE).get();

        Book expectedBook = new Book("testBook", author, genre);
        bookRepository.save(expectedBook);
        assertThat(expectedBook.getId()).isNotBlank();

        Book actualBook = bookRepository.findById(expectedBook.getId()).get();
        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().equals(""))
                .matches(s -> s.getAuthor() != null)
                .matches(s -> s.getGenre() != null);
    }

    @DisplayName("проверять нахождение книги по её id")
    @Test
    public void shouldReturnBookById() {
        List<Book> books = bookRepository.findAll();
        Book expectedBook = books.get(0);
        Book actualBook = bookRepository.findById(expectedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("проверять нахождение книги по её title")
    @Test
    public void shouldReturnBooKByTitle() {
        List<Book> books = bookRepository.findAll();
        Book expectedBook = books.get(0);
        Book actualBook = bookRepository.findByTitle(expectedBook.getTitle()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("проверять нахождение всех книг")
    @Test
    public void shouldReturnExpectedBooksList() {
        Author author1 = authorRepository.findByFullName(EXISTING_FIRST_AUTHOR).get();
        Author author2 = authorRepository.findByFullName(EXISTING_SECOND_AUTHOR).get();

        Genre genre1 = genreRepository.findByName(EXISTING_FIRST_GENRE).get();
        Genre genre2 = genreRepository.findByName(EXISTING_SECOND_GENRE).get();

        List<String> expectedBookTitleList = Arrays.asList(
                new Book(EXISTING_FIRST_BOOK, author1, genre1).getTitle(),
                new Book(EXISTING_SECOND_BOOK, author2, genre2).getTitle()
        );

        List<Book> actualBookList = bookRepository.findAll();
        List<String> actualBookTitleList = new ArrayList<>();

        actualBookList.forEach(book -> actualBookTitleList.add(book.getTitle()));
        assertThat(actualBookTitleList).containsAll(expectedBookTitleList);
    }

    @DisplayName("проверять удаление книги по её id")
    @Test
    public void shouldDeleteCorrectBookById() {
        List<Book> booksBeforeDeleting = bookRepository.findAll();
        int countBeforeDeleting = booksBeforeDeleting.size();

        Book deletingBook = booksBeforeDeleting.get(0);
        assertThat(deletingBook).isNotNull();

        bookRepository.deleteById(deletingBook.getId());
        List<Book> booksAfterDeleting = bookRepository.findAll();
        int countAfterDeleting = booksAfterDeleting.size();

        assertThat(countBeforeDeleting).isNotEqualTo(countAfterDeleting);
        assertThat(deletingBook).isNotIn(booksAfterDeleting);
    }

    @DisplayName("проверять нахождение элементов BookCounts по id книги")
    @Test
    void shouldFindBookCommentsAvgRating() {
        List<Book> books = bookRepository.findAll();
        Book book1 = books.get(0);
        List<BookComments> bookComments = bookRepository.getBookCommentsByBookId(book1.getId());
        assertThat(bookComments).hasSize(BOOK_COUNT)
                .contains(new BookComments(FIRST_BOOK_COMMENTS_COUNT,9));
    }

    @DisplayName("проверять нахождение всех книг по автору")
    @Test
    void shouldFindBooksByAuthor() {
        List<Book> actualBookList = bookRepository.findAllByAuthor_FullName(EXISTING_SECOND_AUTHOR);

        assertThat(actualBookList.get(0).getTitle()).isEqualTo(EXISTING_SECOND_BOOK);
        assertThat(actualBookList.get(0).getAuthor().getFullName()).isEqualTo(EXISTING_SECOND_AUTHOR);
    }

    @DisplayName("проверять нахождение всех книг по жанру")
    @Test
    void shouldFindBooksByGenre() {
        List<Book> actualBookList = bookRepository.findAllByGenre_Name(EXISTING_SECOND_GENRE);

        assertThat(actualBookList.get(0).getTitle()).isEqualTo(EXISTING_SECOND_BOOK);
        assertThat(actualBookList.get(0).getGenre().getName()).isEqualTo(EXISTING_SECOND_GENRE);
    }
}
