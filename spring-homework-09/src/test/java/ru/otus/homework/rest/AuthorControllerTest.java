package ru.otus.homework.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.otus.homework.domain.Author;
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.rest.mappers.AuthorMapperImpl;
import ru.otus.homework.service.authors.AuthorService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("тест AuthorControllerTest должен проверять методы ")
@WebMvcTest(AuthorController.class)
@ContextConfiguration(classes = {
        AuthorMapperImpl.class
})
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthorMapper authorMapper;

    @MockBean
    private AuthorService authorService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AuthorController(authorService, authorMapper)).build();
    }

    @DisplayName("получения всех авторов")
    @Test
    public void shouldReturnAllAuthors() throws Exception {
        List<Author> expectedList = Arrays.asList(
                new Author(1,"author1"),
                new Author(2,"author2"));
        given(authorService.getAllAuthors()).willReturn(expectedList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/author"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("author-list"))
                .andExpect(forwardedUrl("author-list"))
                .andExpect(model().attribute("authors",equalTo(expectedList)));
    }

    @DisplayName("получения одного автора по id для редактирования")
    @Test
    public void shouldReturnOneAuthorForEdit() throws Exception {
        Author author = new Author(1, "author");
        given(authorService.getAuthorById(1)).willReturn(author);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}/edit", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("author-edit"))
                .andExpect(forwardedUrl("author-edit"))
                .andExpect(model().attribute("author",equalTo(author)));
    }

    @DisplayName("обновления существующего автора")
    @Test
    public void shouldUpdateExistingAuthor() throws Exception {
        final String NEW_NAME = "newAuthorName";
        AuthorDto expectedAuthorDto = new AuthorDto(1, NEW_NAME);
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/author/{id}", 1)
                .param("fullName",NEW_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("author", equalTo(expectedAuthorDto)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));
    }

    @DisplayName("создания нового автора")
    @Test
    public void shouldCreateNewAuthor() throws Exception {
        AuthorDto authorDto = new AuthorDto(0, "testAuthor");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/author")
                .param("fullName",authorDto.getFullName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("author", equalTo(authorDto)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));
    }

    @DisplayName("удаления автора по id")
    @Test
    public void shouldDeleteAuthorById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/author/{id}",1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));;
    }

    @DisplayName("получения ошибки при запросе несущетсвующего автора по id")
    @Test
    public void shouldReturn404ByNotExistAuthorId() throws Exception {
        given(authorService.getAuthorById(90)).willThrow(new AuthorException("Author with id [90] not found"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}/edit", 90))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("/error/404"))
                .andExpect(model().attribute("error",equalTo("Author with id [90] not found")));
    }
}
