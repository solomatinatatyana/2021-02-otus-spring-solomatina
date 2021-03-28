package ru.otus.homework.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Student;
import ru.otus.homework.service.survey.SurveyService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тест shell команд ")
@SpringBootTest
public class ShellCommandsTest {

    @MockBean
    private SurveyService surveyService;

    @Autowired
    private Shell shell;

    private static final String LOGIN_PATTERN = "First name: %s\n" + "Second name: %s";
    private static final String DEFAULT_FIRST_NAME = "defaultFirstName";
    private static final String DEFAULT_SECOND_NAME = "defaultSecondName";
    private static final String CUSTOM_FIRST_NAME = "Ivan";
    private static final String CUSTOM_SECOND_NAME = "Ivanov";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_LOGIN_PATTERN= "%s %s %s";
    private static final String COMMAND_START = "start";

    @DisplayName("должен возвращать first name и second name объекта Student для всех форм команд логина")
    @Test()
    public void isShouldReturnFirstAndSecondNameAfterLoginCommand(){
        Student res = (Student) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res.toString()).isEqualTo(String.format(LOGIN_PATTERN, DEFAULT_FIRST_NAME, DEFAULT_SECOND_NAME));

        res = (Student) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res.toString()).isEqualTo(String.format(LOGIN_PATTERN, DEFAULT_FIRST_NAME, DEFAULT_SECOND_NAME));

        res = (Student) shell.evaluate(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_FIRST_NAME, CUSTOM_SECOND_NAME));
        assertThat(res.toString()).isEqualTo(String.format(LOGIN_PATTERN, CUSTOM_FIRST_NAME, CUSTOM_SECOND_NAME));
    }

    @DisplayName("должен возвращать CommandNotCurrentlyAvailable если при попытке выполнения команды start пользователь не выполнил вход")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLoginAfterPublishCommandEvaluated() {
        Object res =  shell.evaluate(() -> COMMAND_START);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("должен проверять, что происходит вызов метода takeASurvey после логина и команды start")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnExpectedMessageAndFirePublishMethodAfterPublishCommandEvaluated() throws IOException {
        shell.evaluate(() -> COMMAND_LOGIN);
        shell.evaluate(() -> COMMAND_START);
        verify(surveyService, times(1)).takeASurvey();
    }

}
