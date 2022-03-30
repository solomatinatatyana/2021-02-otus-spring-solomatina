package ru.otus.homework.rest;

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
import ru.otus.homework.rest.mappers.*;
import ru.otus.homework.security.SecurityConfig;
import ru.otus.homework.security.UserDetailServiceImpl;
import ru.otus.homework.service.authors.AuthorService;
import ru.otus.homework.service.books.BookService;
import ru.otus.homework.service.genres.GenreService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("тест BookControllerWithRolesTest должен проверять методы ")
@WebMvcTest(BookController.class)
@Import(BookController.class)
@ImportAutoConfiguration(LibraryExceptionHandler.class)
@ContextConfiguration(classes = {BookMapperImpl.class, GenreMapperImpl.class, AuthorMapperImpl.class, SecurityConfig.class})
public class BookControllerWithRolesTest {

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
    private UserDetailServiceImpl userDetailService;


    @DisplayName("получения 403 ошибки при запросе страницы с ролью USER")
    @WithMockUser(roles = "USER")
    @Test
    public void shouldReturn403Forbidden() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("получения всех авторов с ролью ADMIN")
    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldReturn200Success() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
