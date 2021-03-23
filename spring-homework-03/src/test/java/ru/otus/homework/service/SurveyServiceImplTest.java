package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.config.SurveyConfig;
import ru.otus.homework.dao.SurveyDao;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Survey;
import ru.otus.homework.exceptions.SurveyException;
import ru.otus.homework.service.survey.SurveyServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@TestPropertySource("classpath:application.yml")
@SpringBootTest
public class SurveyServiceImplTest {

    private static final String TEST_FILE_NAME = "survey.csv";

    @MockBean
    private SurveyDao surveyDao;
    @Autowired
    private SurveyConfig config;
    @Autowired
    private SurveyServiceImpl surveyService;


    @DisplayName("Проверка метода getSurveyByName")
    @Test()
    public void getSurveyByName(){
        given(surveyDao.findSurveyByName(TEST_FILE_NAME))
                .willReturn(Optional.of(new Survey(TEST_FILE_NAME)));
        assertThat(surveyService.getSurveyByName(TEST_FILE_NAME)).isNotNull();
    }

    @DisplayName("Проверка метода getSurveyByName. Должен вернуться SurveyException - Survey not found!")
    @Test()
    public void isShouldReturnSurveyExceptionGetSurveyByName(){
        Throwable exception = assertThrows(SurveyException.class,()->{
            given(surveyDao.findSurveyByName(TEST_FILE_NAME))
                    .willReturn(Optional.empty());
            surveyService.getSurveyByName(TEST_FILE_NAME);
        });
        assertEquals("Survey not found!",exception.getMessage());
    }

    @DisplayName("Проверка метода getQuestions")
    @Test()
    public void getQuestions(){
        List<Question> questionList  = Arrays.asList(
                new Question("1","Вопрос 1"),
                new Question("2","Вопрос 2")
        );
        Survey survey = new Survey(TEST_FILE_NAME);
        given(surveyDao.getQuestions(survey)).willReturn(questionList);
        assertThat(surveyService.getQuestions(survey)).containsAll(questionList);
    }

    @DisplayName("Проверка метода findCorrectAnswers")
    @Test()
    public void findCorrectAnswers(){
        String fileName = "test";
        List<String> answers  = Arrays.asList("1", "2");
        when(surveyDao.findCorrectAnswers(fileName)).thenReturn(answers);
        assertThat(surveyService.getCorrectAnswers(fileName).containsAll(answers));
    }
}
