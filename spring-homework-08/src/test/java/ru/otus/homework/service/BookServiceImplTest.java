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
        given(bookRepository.findByTitle("test")).willReturn(Optional.of(new Book(1,"test",
                new Author(1, "testAuthor"), new Genre(1, "testGenre"))));
        Book actualBook = bookService.getBookByTitle("test");
        assertThat(actualBook).isNotNull();
    }

    @DisplayName("получать книгу по ее id")
    @Test
    public void shouldReturnBookById(){
        given(bookRepository.findById(1L)).willReturn(Optional.of(new Book(1,"test",
                new Author(1, "testAuthor"), new Genre(1, "testGenre"))));
        Book actualBook = bookService.getBookById(1);
        assertThat(actualBook).isNotNull();
    }

    @DisplayName("получать все книги")
    @Test
    public void shouldReturnAllBooks(){
        List<Book> expectedBookList = Arrays.asList(
                new Book(1,"test",
                        new Author(1, "testAuthor"),
                        new Genre(1, "testGenre")),
                new Book(2,"test2",
                        new Author(2, "testAuthor2"),
                        new Genre(2, "testGenre2")));
        given(bookRepository.findAll()).willReturn(expectedBookList);
        List<Book> actualBookList = bookService.getAllBooks();
        assertThat(actualBookList.equals(expectedBookList));
    }

    @DisplayName("получать ошибку при запросе на несуществующую книгу")
    @Test
    public void shouldThrowBookException(){
        Throwable exception = assertThrows(BookException.class,()->{
            given(bookRepository.findById(2L)).willReturn(Optional.empty());
            bookService.getBookById(2);
        });
        assertEquals("Book with id [2] not found",exception.getMessage());
    }

    @DisplayName("получать все книги по жанру")
    @Test
    public void shouldReturnAllBooksByGenre(){
        List<Book> expectedBookList = Arrays.asList(
                new Book(1,"testBookWithGenre",
                        new Author(1, "testAuthor"),
                        new Genre(1, "Fantasy")));
        given(bookRepository.findAll()).willReturn(expectedBookList);
        List<Book> actualBookList = bookService.getAllBooksWithGivenGenre("Fantasy");
        assertThat(actualBookList.equals(expectedBookList));
        assertThat(actualBookList).hasSize(1);
    }

    @DisplayName("получать все книги по автору")
    @Test
    public void shouldReturnAllBooksByAuthor(){
        List<Book> expectedBookList = Arrays.asList(
                new Book(1,"testBookWithGenre",
                        new Author(1, "Tolkien"),
                        new Genre(1, "Fantasy")));
        given(bookRepository.findAll()).willReturn(expectedBookList);
        List<Book> actualBookList = bookService.getAllBooksWithGivenAuthor("Tolkien");
        assertThat(actualBookList.equals(expectedBookList));
        assertThat(actualBookList).hasSize(1);
    }

    @DisplayName("получать все книги которые имеют жанр не равный заданному")
    @Test
    public void shouldReturnAllBooksByNotLikeGenre(){
        List<Book> expectedBookList = Arrays.asList(
                new Book(1,"testBookWithGenre",
                        new Author(1, "testAuthor"),
                        new Genre(1, "Fantasy")));
        given(bookRepository.findAll()).willReturn(expectedBookList);
        List<Book> actualBookList = bookService.getAllBooksNotLikeGenre("Roman");
        assertThat(actualBookList.equals(expectedBookList));
        assertThat(actualBookList).hasSize(1);
    }

    /*@DisplayName("получать количество комментариев по книге")
    @Test
    public void shouldReturnBookCommentsCount(){
        List<BookComments> expectedBookCommentsList = Arrays.asList(
                new BookComments(
                        new Book(1, "test",
                                new Author(1,"testAuthor"),
                                new Genre(1,"testGenre")),2));
        given(bookRepository.findBooksCommentsCount()).willReturn(expectedBookCommentsList);
        List<BookComments> actualBookCommentsList = bookService.getBookCommentsCount();
        assertThat(actualBookCommentsList).hasSize(1);
    }*/

    /*@DisplayName("получать всех книг у которых количество комментариев больше или равно заданному числу")
    @Test
    public void shouldReturnBooksByCommentsCount(){
        List<BookComments> expectedBookList = Arrays.asList(
                new BookComments(new Book(1, "testBook1",
                        new Author(1L, "testAuthor1"),
                        new Genre(1L, "testGenre1")),
                        2),
                new BookComments(new Book(2, "testBook2",
                        new Author(2L, "testAuthor2"),
                        new Genre(2L, "testGenre2")),
                        2)
        );
        given(bookRepository.findBooksByCountCommentsGreaterOrEqualsThan(2L)).willReturn(expectedBookList);
        List<BookComments> actualBookList = bookService.getAllBooksByCountCommentsGreaterOrEqualsThan(2L);
        assertThat(actualBookList).hasSize(2);
    }*/
}
