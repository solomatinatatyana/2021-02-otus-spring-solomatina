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
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.rest.mappers.AuthorMapperImpl;
import ru.otus.homework.service.authors.AuthorService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.homework.util.CommonUtils.mapFromJson;
import static ru.otus.homework.util.CommonUtils.mapToJson;


@DisplayName("тест AuthorControllerTest должен проверять методы ")
@WebMvcTest
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
                new Author("author1"),
                new Author("author2"));
        given(authorService.getAllAuthors()).willReturn(expectedList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/author"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].fullName", is("author1")))
                .andExpect(jsonPath("$[1].fullName", is("author2")));
    }

    @DisplayName("получения одного автора по id для редактирования")
    @Test
    public void shouldReturnOneAuthorForEdit() throws Exception {
        Author author = new Author("1", "author");
        given(authorService.getAuthorById("1")).willReturn(author);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/author/{id}/edit", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Author authorResponse = mapFromJson(content,Author.class);
        Assertions.assertEquals(author,authorResponse);
    }

    @DisplayName("обновления существующего автора")
    @Test
    public void shouldUpdateExistingAuthor() throws Exception {
        final String NEW_NAME = "newAuthorName";
        AuthorDto expectedAuthorDto = new AuthorDto("1",NEW_NAME);
        String authorDtoJson = mapToJson(expectedAuthorDto);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/author/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorDtoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        AuthorDto authorDtoResponse = mapFromJson(content,AuthorDto.class);
        Assertions.assertEquals(expectedAuthorDto,authorDtoResponse);
    }

    @DisplayName("создания нового автора")
    @Test
    public void shouldCreateNewAuthor() throws Exception {
        AuthorDto authorDto = new AuthorDto("1","testAuthor");
        String authorDtoJson = mapToJson(authorDto);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorDtoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        AuthorDto authorDtoResponse = mapFromJson(content,AuthorDto.class);
        Assertions.assertEquals(authorDto,authorDtoResponse);
    }

    @DisplayName("удаления автора по id")
    @Test
    public void shouldDeleteAuthorById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/author/{id}",1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }

    @DisplayName("получения ошибки при запросе несущетсвующего автора по id")
    @Test
    public void shouldReturn404ByNotExistAuthorId() throws Exception {
        given(authorService.getAuthorById("90")).willThrow(new AuthorException("Author with id [90] not found"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/author/{id}/edit", 90))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("/error/404"))
                .andExpect(model().attribute("error",equalTo("Author with id [90] not found")));
    }
}
