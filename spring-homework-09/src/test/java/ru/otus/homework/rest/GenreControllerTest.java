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
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.rest.dto.GenreDto;
import ru.otus.homework.rest.mappers.BookMapperImpl;
import ru.otus.homework.rest.mappers.GenreMapper;
import ru.otus.homework.rest.mappers.GenreMapperImpl;
import ru.otus.homework.service.genres.GenreService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.homework.util.CommonUtils.mapFromJson;
import static ru.otus.homework.util.CommonUtils.mapToJson;

@DisplayName("тест GenreControllerTest должен проверять методы ")
@WebMvcTest(GenreController.class)
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

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new GenreController(genreService, genreMapper)).build();
    }

    @DisplayName("получения всех жанров")
    @Test
    public void shouldReturnAllGenres() throws Exception {
        List<Genre> expectedList = Arrays.asList(
                new Genre("genre1"),
                new Genre("genre2"));
        given(genreService.getAllGenres()).willReturn(expectedList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/genre"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name", is("genre1")))
                .andExpect(jsonPath("$[1].name", is("genre2")));;
    }

    @DisplayName("получения одного жанра по id для редактирования")
    @Test
    public void shouldReturnOneAuthorForEdit() throws Exception {
        Genre genre = new Genre("1" ,"genre");
        given(genreService.getGenreById("1")).willReturn(genre);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/genre/{id}/edit", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Genre genreResponse = mapFromJson(content,Genre.class);
        Assertions.assertEquals(genre,genreResponse);
    }

    @DisplayName("обновления существующего жанра")
    @Test
    public void shouldUpdateExistingGenre() throws Exception {
        final String NEW_NAME = "newGenreName";
        GenreDto expectedGenreDto = new GenreDto("1", NEW_NAME);
        String genreDtoJson = mapToJson(expectedGenreDto);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/genre/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(genreDtoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        GenreDto genreDtoResponse = mapFromJson(content,GenreDto.class);
        Assertions.assertEquals(expectedGenreDto,genreDtoResponse);
    }

    @DisplayName("создания нового жанра")
    @Test
    public void shouldCreateNewGenre() throws Exception {
        GenreDto genreDto = new GenreDto( "testGenre");
        String genreDtoJson = mapToJson(genreDto);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(genreDtoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        GenreDto authorDtoResponse = mapFromJson(content,GenreDto.class);
        Assertions.assertEquals(genreDto,authorDtoResponse);
    }

    @DisplayName("удаления жанра по id")
    @Test
    public void shouldDeleteGenreById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/genre/{id}",1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }

    @DisplayName("получения ошибки при запросе несущетсвующего жанра по id")
    @Test
    public void shouldReturn404ByNotExistGenreId() throws Exception {
        given(genreService.getGenreById("90")).willThrow(new GenreException("Genre with id [90] not found"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/genre/{id}/edit", 90))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("/error/404"))
                .andExpect(model().attribute("error",equalTo("Genre with id [90] not found")));
    }
}
