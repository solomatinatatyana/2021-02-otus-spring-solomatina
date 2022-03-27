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
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.rest.mappers.AuthorMapperImpl;
import ru.otus.homework.security.SecurityConfig;
import ru.otus.homework.security.UserDetailServiceImpl;
import ru.otus.homework.service.authors.AuthorService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("тест AuthorControllerWithRolesTest должен проверять методы ")
@WebMvcTest(AuthorController.class)
@Import(AuthorController.class)
@ImportAutoConfiguration(LibraryExceptionHandler.class)
@ContextConfiguration(classes = {AuthorMapperImpl.class, SecurityConfig.class})
public class AuthorControllerWithRolesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorMapper authorMapper;

    @MockBean
    private AuthorService service;

    @MockBean
    private UserDetailServiceImpl userDetailService;


    @DisplayName("получения 403 ошибки при запросе страницы с неверной ролью")
    @WithMockUser(roles = "USER")
    @Test
    public void shouldReturn403Forbidden() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/author"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @DisplayName("получения всех авторов с верной ролью")
    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldReturn200Success() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/author"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
