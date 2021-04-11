package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;
import ru.otus.homework.repository.book.BookRepositoryJpa;
import ru.otus.homework.repository.book.BookRepositoryJpaImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест класса BookRepositoryJpaImpl должен ")
@DataJpaTest
@Import(BookRepositoryJpaImpl.class)
public class BookRepositoryJpaImplTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final long EXISTING_FIRST_BOOK_ID = 1L;
    private static final long EXISTING_SECOND_BOOK_ID = 2L;
    private static final String EXISTING_SECOND_BOOK = "War and peace";
    private static final String EXISTING_SECOND_AUTHOR = "Tolstoy";
    private static final String EXISTING_SECOND_GENRE = "Roman";
    private static final String BOOK_TITLE = "updatedTitle";
    private static final int BOOK_COUNT = 2;
    private static final int FIRST_BOOK_COMMENTS_COUNT = 2;

    @DisplayName("проверять добавление новой книги в БД")
    @Test
    public void shouldInsertNewBook() {
        Author author = new Author(1, EXISTING_SECOND_AUTHOR);
        Genre genre = new Genre(1, EXISTING_SECOND_GENRE);
        Comment comment = new Comment(1, "testComment",0);
        List<Comment> comments = Collections.singletonList(comment);

        Book expectedBook = new Book(3, "testBook", author, genre, comments);
        bookRepositoryJpa.insert(expectedBook);
        assertThat(expectedBook.getId()).isGreaterThan(0);

        Book actualBook = em.find(Book.class, expectedBook.getId());
        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().equals(""))
                .matches(s -> s.getAuthor() != null)
                .matches(s -> s.getGenre() != null)
                .matches(s -> s.getComments() != null && s.getComments().size() > 0);
    }

    @DisplayName("проверять нахождение книги по её id")
    @Test
    public void shouldReturnBookById() {
        Book expectedBook = em.find(Book.class, EXISTING_FIRST_BOOK_ID);
        Book actualBook = bookRepositoryJpa.findById(expectedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("проверять нахождение книги по её title")
    @Test
    public void shouldReturnBooKByTitle() {
        Book expectedBook = em.find(Book.class, EXISTING_FIRST_BOOK_ID);
        Book actualBook = bookRepositoryJpa.findByName(expectedBook.getTitle()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("проверять нахождение всех книг")
    @Test
    public void shouldReturnExpectedBooksList() {
        List<Book> expectedBookList = Arrays.asList(
                em.find(Book.class, EXISTING_FIRST_BOOK_ID),
                em.find(Book.class, EXISTING_SECOND_BOOK_ID)
        );
        List<Book> actualBookList = bookRepositoryJpa.findAll();
        assertThat(actualBookList).containsAll(expectedBookList);
    }

    @DisplayName("проверять обновление названия книги по её id")
    @Test
    public void shouldUpdateBookById() {
        Book firstBook = em.find(Book.class, EXISTING_FIRST_BOOK_ID);
        String oldName = firstBook.getTitle();
        em.detach(firstBook);

        bookRepositoryJpa.updateTitleById(EXISTING_FIRST_BOOK_ID, BOOK_TITLE);
        Book updatedBook = em.find(Book.class, EXISTING_FIRST_BOOK_ID);

        assertThat(updatedBook.getTitle()).isNotEqualTo(oldName).isEqualTo(BOOK_TITLE);
    }

    @DisplayName("проверять удаление книги по её id")
    @Test
    public void shouldDeleteCorrectBookById() {
        Book secondBook = em.find(Book.class, EXISTING_SECOND_BOOK_ID);
        assertThat(secondBook).isNotNull();
        em.detach(secondBook);

        bookRepositoryJpa.deleteById(EXISTING_SECOND_BOOK_ID);
        Book deletedGenre = em.find(Book.class, EXISTING_SECOND_BOOK_ID);

        assertThat(deletedGenre).isNull();
    }

    @DisplayName("проверять удаление книги по её title")
    @Test
    public void shouldDeleteCorrectGenreByTitle() {
        Book secondBook = em.find(Book.class, EXISTING_SECOND_BOOK_ID);
        assertThat(secondBook).isNotNull();
        em.detach(secondBook);

        bookRepositoryJpa.deleteByName(EXISTING_SECOND_BOOK);
        Book deletedGenre = em.find(Book.class, EXISTING_SECOND_BOOK_ID);

        assertThat(deletedGenre).isNull();
    }

    @DisplayName("проверять нахождение количества комметариев по книгам")
    @Test
    void shouldFindBookCommentsCount() {
        Book book1 = em.find(Book.class, EXISTING_FIRST_BOOK_ID);
        List<BookComments> bookComments = bookRepositoryJpa.findBooksCommentsCount();
        assertThat(bookComments).hasSize(BOOK_COUNT)
                .contains(new BookComments(book1, FIRST_BOOK_COMMENTS_COUNT));
    }

    @DisplayName("проверять нахождение книг по жанру")
    @Test
    void shouldFindBooksByGenre() {
        Book book = em.find(Book.class, EXISTING_FIRST_BOOK_ID);
        List<Book> books = bookRepositoryJpa.findAllBooksWithGivenGenre("Fantasy");
        assertThat(books).hasSize(1).containsExactlyInAnyOrder(book);
    }
}
