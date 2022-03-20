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
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.repository.user.UserRepository;
import ru.otus.homework.rest.dto.GenreDto;
import ru.otus.homework.rest.mappers.BookMapperImpl;
import ru.otus.homework.rest.mappers.GenreMapper;
import ru.otus.homework.rest.mappers.GenreMapperImpl;
import ru.otus.homework.security.UserDetailServiceImpl;
import ru.otus.homework.service.genres.GenreService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@DisplayName("тест GenreControllerTest должен проверять методы ")
@WebMvcTest(GenreController.class)
@Import(GenreController.class)
@ImportAutoConfiguration(LibraryExceptionHandler.class)
@ContextConfiguration(classes = {
        GenreMapperImpl.class, BookMapperImpl.class
})
public class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GenreMapper genreMapper;

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
                .perform(MockMvcRequestBuilders.get("/genre"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("успешного входа в систему под пользователем")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    @Test
    public void shouldReturn200forUser() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/genre"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("успешного входа в систему под администратором")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturn200forAdmin() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/genre"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("получения всех жанров")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturnAllGenres() throws Exception {
        List<Genre> expectedList = Arrays.asList(
                new Genre(1,"genre1"),
                new Genre(2,"genre2"));
        given(genreService.getAllGenres()).willReturn(expectedList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/genre"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("genre-list"))
                .andExpect(model().attribute("genres",equalTo(expectedList)));
    }

    @DisplayName("получения одного жанра по id для редактирования")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturnOneAuthorForEdit() throws Exception {
        Genre genre = new Genre(1, "genre");
        given(genreService.getGenreById(1)).willReturn(genre);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/genre/{id}/edit", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("genre-edit"))
                .andExpect(model().attribute("genre",equalTo(genre)));
    }

    @DisplayName("обновления существующего жанра")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldUpdateExistingGenre() throws Exception {
        final String NEW_NAME = "newGenreName";
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/genre/{id}", 1).with(csrf())
                .param("name",NEW_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genre"));
    }

    @DisplayName("создания нового жанра")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldCreateNewGenre() throws Exception {
        GenreDto genreDto = new GenreDto(0, "testGenre");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/genre").with(csrf())
                .param("name",genreDto.getName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genre"));
    }

    @DisplayName("удаления жанра по id")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldDeleteGenreById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/genre/{id}",1).with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genre"));;
    }

    @DisplayName("получения ошибки при запросе несущетсвующего жанра по id")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturn404ByNotExistGenreId() throws Exception {
        given(genreService.getGenreById(90)).willThrow(new GenreException("Genre with id [90] not found"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/genre/{id}/edit", 90))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("/error/404"))
                .andExpect(model().attribute("error",equalTo("Genre with id [90] not found")));
    }
}
