package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.dao.book.BookDao;
import ru.otus.homework.domain.Book;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.service.books.BookServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@TestPropertySource("classpath:application.yml")
@DisplayName("Сервис BookService должен ")
@SpringBootTest
public class BookServiceImplTest {
    @MockBean
    private BookDao bookDao;
    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("получать книгу по ее title")
    @Test
    public void shouldReturnBookByTitle(){
        given(bookDao.findByName("test")).willReturn(Optional.of(new Book(1,"test", 1, 1)));
        Book actualBook = bookService.getBookByTitle("test");
        assertThat(actualBook).isNotNull();
    }

    @DisplayName("получать книгу по ее id")
    @Test
    public void shouldReturnBookById(){
        given(bookDao.findById(1)).willReturn(Optional.of(new Book(1,"test", 1, 1)));
        Book actualBook = bookService.getBookById(1);
        assertThat(actualBook).isNotNull();
    }

    @DisplayName("получать все книги")
    @Test
    public void shouldReturnAllBooks(){
        List<Book> expectedBookList = Arrays.asList(
                new Book(1,"testBook1", 1, 1),
                new Book(2,"testBook2", 2, 2));
        given(bookDao.findAll()).willReturn(expectedBookList);
        List<Book> actualBookList = bookService.getAllBooks();
        assertThat(actualBookList.equals(expectedBookList));
    }

    @DisplayName("получать ошибку при запросе на несуществующую книгу")
    @Test
    public void shouldThrowBookException(){
        Throwable exception = assertThrows(BookException.class,()->{
            given(bookDao.findById(2)).willReturn(Optional.empty());
            bookService.getBookById(2);
        });
        assertEquals("Book with id [2] not found",exception.getMessage());
    }

}
