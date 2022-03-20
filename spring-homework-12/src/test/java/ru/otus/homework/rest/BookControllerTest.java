package ru.otus.homework.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.repository.user.UserRepository;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.rest.mappers.*;
import ru.otus.homework.security.UserDetailServiceImpl;
import ru.otus.homework.service.authors.AuthorService;
import ru.otus.homework.service.books.BookService;
import ru.otus.homework.service.genres.GenreService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@DisplayName("тест BookControllerTest должен проверять методы ")
@WebMvcTest(BookController.class)
@Import(BookController.class)
@ImportAutoConfiguration(LibraryExceptionHandler.class)
@ContextConfiguration(classes = {
        BookMapperImpl.class, GenreMapperImpl.class, AuthorMapperImpl.class
})
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private AuthorMapper authorMapper;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @MockBean
    private UserDetailServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @DisplayName("получения 401 ошибки без указания пользователя")
    @Test
    public void shouldReturn401Unauthorized() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("успешного входа в систему под пользователем")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    @Test
    public void shouldReturn200forUser() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("успешного входа в систему под администратором")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturn200forAdmin() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }


    @DisplayName("получения всех книг")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturnAllBooks() throws Exception {
        List<Book> expectedList = Arrays.asList(
                new Book(1,"book1",
                        new Author(1, "author1"),
                        new Genre(1, "genre1")),
                new Book(1,"book2",
                        new Author(2, "author2"),
                        new Genre(2, "genre2")));
        given(bookService.getAllBooks()).willReturn(expectedList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("book-list"))
                .andExpect(model().attribute("books",equalTo(expectedList)));
    }

    @DisplayName("получения всех книг по автору")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturnBooksByAuthor() throws Exception {
        List<Book> expectedList = Arrays.asList(
                new Book(1,"book1",
                        new Author(1, "author1"),
                        new Genre(1, "genre1")),
                new Book(1,"book2",
                        new Author(2, "author1"),
                        new Genre(2, "genre2")));
        given(bookService.getAllBooksWithGivenAuthor("author1")).willReturn(expectedList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .queryParam("author", "author1").queryParam("genre",""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("book-list"))
                .andExpect(model().attribute("books",equalTo(expectedList)));
    }

    @DisplayName("получения всех книг по жанру")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturnBooksByGenre() throws Exception {
        List<Book> expectedList = Arrays.asList(
                new Book(1,"book1",
                        new Author(1, "author1"),
                        new Genre(1, "genre1")),
                new Book(1,"book2",
                        new Author(2, "author2"),
                        new Genre(2, "genre1")));
        given(bookService.getAllBooksWithGivenGenre("genre1")).willReturn(expectedList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .queryParam("author", "").queryParam("genre","genre1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("book-list"))
                .andExpect(model().attribute("books",equalTo(expectedList)));
    }

    @DisplayName("получения одной книги по id для редактирования")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturnOneBookForEdit() throws Exception {
        Book book = new Book(1,"book",
                new Author(1, "author"),
                new Genre(1, "genre"));
        given(bookService.getBookById(1)).willReturn(book);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}/edit", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("book-edit"));
    }

    @DisplayName("обновления существующего жанра")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldUpdateExistingBook() throws Exception {
        final String NEW_NAME = "newBookName";
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/book/{id}", 1).with(csrf())
                .param("title",NEW_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
    }

    @DisplayName("создания новой книги")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldCreateNewBook() throws Exception {
        BookDto bookDto = new BookDto(0, "testBook");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/book").with(csrf())
                .param("title",bookDto.getTitle()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
    }

    @DisplayName("удаления книги по id")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldDeleteBookById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/book/{id}",1).with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));
    }

    @DisplayName("получения ошибки при запросе несущетсвующей книги по id")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturn404ByNotExistBookId() throws Exception {
        given(bookService.getBookById(90)).willThrow(new BookException("Book with id [90] not found"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}/edit", 90))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("/error/404"))
                .andExpect(model().attribute("error",equalTo("Book with id [90] not found")));
    }
}
