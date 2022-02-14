package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.repository.author.AuthorRepository;
import ru.otus.homework.repository.book.BookRepository;
import ru.otus.homework.repository.genre.GenreRepository;
import ru.otus.homework.service.books.BookServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@TestPropertySource("classpath:application.yaml")
@DisplayName("Сервис BookService должен ")
@SpringBootTest
public class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private AuthorRepository authorRepository;
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
        final String GENRE = "Fantasy";
        Genre genre = new Genre("1", GENRE);
        List<Book> expectedBookList = Arrays.asList(
                new Book("testBookWithGenre",
                        new Author("1", "testAuthor"),
                       genre));
        given(bookRepository.findAll()).willReturn(expectedBookList);
        when(genreRepository.findByName(GENRE)).thenReturn(Optional.of(genre));
        List<Book> actualBookList = bookService.getAllBooksWithGivenGenre("Fantasy");
        assertThat(actualBookList.equals(expectedBookList));
        assertThat(actualBookList).hasSize(1);
    }

    @DisplayName("получать все книги по автору")
    @Test
    public void shouldReturnAllBooksByAuthor(){
        final String AUTHOR = "Джон Толкин";
        Author author = new Author("1", AUTHOR);
        List<Book> expectedBookList = Arrays.asList(
                new Book("testBookWithAuthor",
                        author,
                        new Genre("1", "Фантастика")));
        given(bookRepository.findAll()).willReturn(expectedBookList);
        when(authorRepository.findByFullName(AUTHOR)).thenReturn(Optional.of(author));
        List<Book> actualBookList = bookService.getAllBooksWithGivenAuthor("Джон Толкин");
        assertThat(actualBookList.equals(expectedBookList));
        assertThat(actualBookList).hasSize(1);
    }

    @DisplayName("проверять нахождение количества комметариев по книге")
    @Test
    void shouldFindBookCommentsCount() {
        Book book = new Book("1","book1",
                new Author("1", "author1"),
                new Genre("1", "genre1"),
                new Comment("1","text1",10),
                new Comment("2","text2",9));
        given(bookRepository.findAll()).willReturn(Arrays.asList(book));
        int countComments = book.getComments().size();
        double actualAvg = bookService.getBookCommentsCount(book.getId());
        assertThat(actualAvg).isEqualTo(countComments);
    }

    @DisplayName("проверять нахождение средней оценки по книге")
    @Test
    void shouldFindBookCommentsAvgRating() {
        Book book = new Book("1","book1",
                new Author("1", "author1"),
                new Genre("1", "genre1"),
                new Comment("1","text1",10),
                new Comment("2","text2",9));
        given(bookRepository.findAll()).willReturn(Arrays.asList(book));
        double expectedAvg = (double)(book.getComments().get(0).getRating() + book.getComments().get(1).getRating())/2;
        book.setAvgRating(expectedAvg);
        double actualAvg = bookService.getAvgRatingComments(book.getId());
        assertThat(book.getAvgRating()).isEqualTo(actualAvg);
    }
}
