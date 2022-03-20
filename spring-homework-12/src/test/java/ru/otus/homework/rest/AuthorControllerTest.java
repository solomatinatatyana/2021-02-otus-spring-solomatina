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
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.repository.user.UserRepository;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.rest.mappers.AuthorMapperImpl;
import ru.otus.homework.security.UserDetailServiceImpl;
import ru.otus.homework.service.authors.AuthorService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@DisplayName("тест AuthorControllerTest должен проверять методы ")
@WebMvcTest(AuthorController.class)
@Import(AuthorController.class)
@ImportAutoConfiguration(LibraryExceptionHandler.class)
@ContextConfiguration(classes = {AuthorMapperImpl.class})
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthorMapper authorMapper;

    @MockBean
    private AuthorService authorService;

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
                .perform(MockMvcRequestBuilders.get("/author"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("успешного входа в систему под пользователем")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    @Test
    public void shouldReturn200forUser() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/author"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("успешного входа в систему под администратором")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturn200forAdmin() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/author"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("получения всех авторов")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturnAllAuthors() throws Exception {
        List<Author> expectedList = Arrays.asList(
                new Author(1,"author1"),
                new Author(2,"author2"));
        given(authorService.getAllAuthors()).willReturn(expectedList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/author"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("author-list"))
                .andExpect(model().attribute("authors",equalTo(expectedList)));
    }

    @DisplayName("получения одного автора по id для редактирования")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturnOneAuthorForEdit() throws Exception {
        Author author = new Author(1, "author");
        given(authorService.getAuthorById(1)).willReturn(author);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}/edit", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("author-edit"))
                .andExpect(model().attribute("author",equalTo(author)));
    }

    @DisplayName("обновления существующего автора")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldUpdateExistingAuthor() throws Exception {
        final String NEW_NAME = "newAuthorName";
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/author/{id}", 1).with(csrf())
                .param("fullName",NEW_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));
    }

    @DisplayName("создания нового автора")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldCreateNewAuthor() throws Exception {
        AuthorDto authorDto = new AuthorDto(0, "testAuthor");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/author").with(csrf())
                .param("fullName",authorDto.getFullName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));
    }

    @DisplayName("удаления автора по id")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldDeleteAuthorById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/author/{id}",1).with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));;
    }

    @DisplayName("получения ошибки при запросе несущетсвующего автора по id")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturn404ByNotExistAuthorId() throws Exception {
        given(authorService.getAuthorById(90)).willThrow(new AuthorException("Author with id [90] not found"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}/edit", "90"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("/error/404"))
                .andExpect(model().attribute("error",equalTo("Author with id [90] not found")));
    }
}
