package ru.otus.homework.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.rest.dto.CommentDto;
import ru.otus.homework.rest.dto.GenreDto;
import ru.otus.homework.rest.mappers.*;
import ru.otus.homework.service.authors.AuthorService;
import ru.otus.homework.service.books.BookService;
import ru.otus.homework.service.genres.GenreService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework.util.CommonUtils.*;

@DisplayName("тест BookControllerTest должен проверять методы ")
@WebMvcTest(BookController.class)
@ContextConfiguration(classes = {
        BookMapperImpl.class, GenreMapperImpl.class, AuthorMapperImpl.class, CommentMapperImpl.class
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
    @Autowired
    private CommentMapper commentMapper;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BookController(
                bookService, authorService, genreService, bookMapper, genreMapper, authorMapper, commentMapper)).build();
    }

    @DisplayName("получения всех книг")
    @Test
    public void shouldReturnAllBooks() throws Exception {
        Book book1 = new Book("1","book1",
                new Author("1", "author1"),
                new Genre("1", "genre1"),
                new Comment("1","text1",0));
        Book book2 = new Book("2","book2",
                new Author("2", "author2"),
                new Genre("2", "genre2"),
                new Comment("1","text2",0));
        List<Book> expectedList = Arrays.asList(book1, book2);
        given(bookService.getAllBooks()).willReturn(expectedList);
        when(bookService.getCommentsByBookId(book1.getId())).thenReturn(book1.getComments());
        when(bookService.getCommentsByBookId(book2.getId())).thenReturn(book2.getComments());
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        List<Book> actualList = mapListFromJson(result.getResponse().getContentAsString(), Book.class);
        Assertions.assertEquals(expectedList,actualList);
    }

    @DisplayName("получения всех книг по автору")
    @Test
    public void shouldReturnBooksByAuthor() throws Exception {
        Book book1 = new Book("1","book1",
                new Author("1", "author1"),
                new Genre("1", "genre1"),
                new Comment("1","text1",0));
        Book book2 = new Book("2","book2",
                new Author("2", "author2"),
                new Genre("2", "genre2"),
                new Comment("1","text2",0));
        List<Book> expectedList = Arrays.asList(book1, book2);
        given(bookService.getAllBooksWithGivenAuthor("author1")).willReturn(expectedList);
        when(bookService.getCommentsByBookId(book1.getId())).thenReturn(book1.getComments());
        when(bookService.getCommentsByBookId(book2.getId())).thenReturn(book2.getComments());
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/book")
                .queryParam("author", "author1").queryParam("genre",""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        List<Book> actualList = mapListFromJson(result.getResponse().getContentAsString(), Book.class);
        Assertions.assertEquals(expectedList,actualList);
    }

    @DisplayName("получения всех книг по жанру")
    @Test
    public void shouldReturnBooksByGenre() throws Exception {
        Book book1 = new Book("1","book1",
                new Author("1", "author1"),
                new Genre("1", "genre1"),
                new Comment("1","text1",0));
        Book book2 = new Book("2","book2",
                new Author("2", "author2"),
                new Genre("2", "genre2"),
                new Comment("1","text2",0));
        List<Book> expectedList = Arrays.asList(book1, book2);
        given(bookService.getAllBooksWithGivenGenre("genre1")).willReturn(expectedList);
        when(bookService.getCommentsByBookId(book1.getId())).thenReturn(book1.getComments());
        when(bookService.getCommentsByBookId(book2.getId())).thenReturn(book2.getComments());
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/book")
                .queryParam("author", "").queryParam("genre","genre1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        List<Book> bookListResponse = mapListFromJson(result.getResponse().getContentAsString(), Book.class);
        Assertions.assertEquals(expectedList,bookListResponse);
    }


    @DisplayName("обновления существующего жанра")
    @Test
    public void shouldUpdateExistingBook() throws Exception {
        List<CommentDto> commentsDtos = Arrays.asList(new CommentDto("1","comment",0));
        final String NEW_NAME = "{\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"newBookTitle\"\n" +
                "}";
        String patchToDto = mapToJson(NEW_NAME);
        BookDto bookDto = new BookDto( "1","testBook",
                new AuthorDto("testAuthor"),
                new GenreDto("testGenre"),
                commentsDtos,0.0);
        String bookDtoJson = mapToJson(bookDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful()).andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/book/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchToDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("title", is(NEW_NAME)));
    }

    @DisplayName("создания новой книги")
    @Test
    public void shouldCreateNewBook() throws Exception {
        List<CommentDto> commentsDtos = Arrays.asList(new CommentDto("1","comment",0));
        BookDto bookDto = new BookDto( "1","testBook",
                new AuthorDto("testAuthor"),
                new GenreDto("testGenre"),
                commentsDtos,0.0);
        String bookDtoJson = mapToJson(bookDto);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookDtoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        BookDto bookDtoResponse = mapFromJson(content,BookDto.class);
        Assertions.assertEquals(bookDto,bookDtoResponse);
    }

    @DisplayName("удаления книги по id")
    @Test
    public void shouldDeleteBookById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/{id}",1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }
}
