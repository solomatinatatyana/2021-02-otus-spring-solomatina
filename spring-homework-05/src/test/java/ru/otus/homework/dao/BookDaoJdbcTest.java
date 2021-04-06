package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.dao.book.BookDao;
import ru.otus.homework.dao.book.BookDaoJdbc;
import ru.otus.homework.domain.Book;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тест класса BookDaoJdbc должен ")
@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {
    private static final int EXISTING_FIRST_BOOK_ID = 1;
    private static final String EXISTING_FIRST_BOOK = "The lord of the rings";
    private static final int EXISTING_SECOND_BOOK_ID = 2;
    private static final String EXISTING_SECOND_BOOK = "War and peace";


    @Autowired
    private BookDao bookDao;

    @DisplayName("проверять добавление новой книги в БД")
    @Test
    public void shouldInsertNewBook() {
        Book expectedBook = new Book(3, "testGenre", 2, 1);
        bookDao.insert(expectedBook);
        Book actualBook = bookDao.findById(expectedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("проверять нахождение книги по её id")
    @Test
    public void shouldReturnBookById() {
        Book expectedBook = new Book(EXISTING_FIRST_BOOK_ID, EXISTING_FIRST_BOOK, 1, 1);
        Book actualBook = bookDao.findById(expectedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("проверять нахождение книги по её title")
    @Test
    public void shouldReturnBooKByTitle() {
        Book expectedBook = new Book(EXISTING_FIRST_BOOK_ID, EXISTING_FIRST_BOOK, 1, 1);
        Book actualBook = bookDao.findByName(expectedBook.getTitle()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("проверять нахождение всех книг")
    @Test
    public void shouldReturnExpectedBooksList() {
        List<Book> expectedBookList = Arrays.asList(
                new Book(EXISTING_FIRST_BOOK_ID, EXISTING_FIRST_BOOK, 1, 1),
                new Book(EXISTING_SECOND_BOOK_ID, EXISTING_SECOND_BOOK, 2, 2)
        );
        List<Book> actualBookList = bookDao.findAll();
        assertThat(actualBookList).containsAll(expectedBookList);
    }

    @DisplayName("проверять обновление названия книги по её id")
    @Test
    public void shouldUpdateBookById() {
        String expectedTitle = "updateTittle";
        Book expectedBook = new Book(EXISTING_FIRST_BOOK_ID, expectedTitle, 1, 1);
        bookDao.updateTitleById(EXISTING_FIRST_BOOK_ID, expectedTitle);
        Book actualBook = bookDao.findById(expectedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("проверять удаление книги по её id")
    @Test
    public void shouldDeleteCorrectBookById() {
        assertThatCode(() -> bookDao.findById(EXISTING_SECOND_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_SECOND_BOOK_ID);

        assertThatThrownBy(() -> bookDao.findById(EXISTING_SECOND_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("проверять удаление книги по её title")
    @Test
    public void shouldDeleteCorrectGenreByTitle() {
        assertThatCode(() -> bookDao.findById(EXISTING_SECOND_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteByName(EXISTING_SECOND_BOOK);

        assertThatThrownBy(() -> bookDao.findById(EXISTING_SECOND_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
