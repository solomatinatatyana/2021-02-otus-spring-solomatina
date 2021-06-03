package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.repository.book.BookRepository;
import ru.otus.homework.service.books.BookServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@TestPropertySource("classpath:application.yaml")
@DisplayName("Сервис BookService должен ")
@SpringBootTest
public class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("получать книгу по ее title")
    @Test
    public void shouldReturnBookByTitle(){
        given(bookRepository.findByTitle("test")).willReturn(Optional.of(new Book("test",
                new Author("1", "testAuthor"), new Genre("1", "testGenre"))));
        Book actualBook = bookService.getBookByTitle("test");
        assertThat(actualBook).isNotNull();
    }

    @DisplayName("получать книгу по ее id")
    @Test
    public void shouldReturnBookById(){
        given(bookRepository.findById("1")).willReturn(Optional.of(new Book("test",
                new Author("1", "testAuthor"), new Genre("1", "testGenre"))));
        Book actualBook = bookService.getBookById("1");
        assertThat(actualBook).isNotNull();
    }

    @DisplayName("получать все книги")
    @Test
    public void shouldReturnAllBooks(){
        List<Book> expectedBookList = Arrays.asList(
                new Book("test",
                        new Author("1", "testAuthor"),
                        new Genre("1", "testGenre")),
                new Book("test2",
                        new Author("2", "testAuthor2"),
                        new Genre("2", "testGenre2")));
        given(bookRepository.findAll()).willReturn(expectedBookList);
        List<Book> actualBookList = bookService.getAllBooks();
        assertThat(actualBookList.equals(expectedBookList));
    }

    @DisplayName("получать ошибку при запросе на несуществующую книгу")
    @Test
    public void shouldThrowBookException(){
        Throwable exception = assertThrows(BookException.class,()->{
            given(bookRepository.findById("2")).willReturn(Optional.empty());
            bookService.getBookById("2");
        });
        assertEquals("Book with id [2] not found",exception.getMessage());
    }

    @DisplayName("получать все книги по жанру")
    @Test
    public void shouldReturnAllBooksByGenre(){
        List<Book> expectedBookList = Arrays.asList(
                new Book("testBookWithGenre",
                        new Author("1", "testAuthor"),
                        new Genre("1", "Fantasy")));
        given(bookRepository.findAll()).willReturn(expectedBookList);
        List<Book> actualBookList = bookService.getAllBooksWithGivenGenre("Fantasy");
        assertThat(actualBookList.equals(expectedBookList));
        assertThat(actualBookList).hasSize(1);
    }

    @DisplayName("получать все книги по автору")
    @Test
    public void shouldReturnAllBooksByAuthor(){
        List<Book> expectedBookList = Arrays.asList(
                new Book("testBookWithGenre",
                        new Author("1", "Tolkien"),
                        new Genre("1", "Fantasy")));
        given(bookRepository.findAll()).willReturn(expectedBookList);
        List<Book> actualBookList = bookService.getAllBooksWithGivenAuthor("Tolkien");
        assertThat(actualBookList.equals(expectedBookList));
        assertThat(actualBookList).hasSize(1);
    }

    @DisplayName("проверять нахождение количества комметариев по книге")
    @Test
    void shouldFindBookCommentsCount() {
        Book expectedBook = new Book("testBookWithGenre",
                        new Author("1", "Tolkien"),
                        new Genre("1", "Fantasy"));
        expectedBook.setId("1");
        List<BookComments> expectedBookComments = List.of(new BookComments(2, 9));
        given(bookRepository.getBookCommentsByBookId("1")).willReturn(expectedBookComments);

        long actualCount = bookService.getBookCommentsCount(expectedBook.getId());
        assertThat(expectedBookComments.get(0).getCommentsCount()).isEqualTo(actualCount);
    }

    @DisplayName("проверять нахождение средней оценки по книге")
    @Test
    void shouldFindBookCommentsAvgRating() {
        Book expectedBook = new Book("testBookWithGenre",
                new Author("1", "Tolkien"),
                new Genre("1", "Fantasy"));
        expectedBook.setId("1");
        List<BookComments> expectedBookComments = List.of(new BookComments(2, 9));
        given(bookRepository.getBookCommentsByBookId("1")).willReturn(expectedBookComments);

        double actualAvg = bookService.getAvgRatingComments(expectedBook.getId());
        assertThat(expectedBookComments.get(0).getAvgCommentsRating()).isEqualTo(actualAvg);
    }
}
