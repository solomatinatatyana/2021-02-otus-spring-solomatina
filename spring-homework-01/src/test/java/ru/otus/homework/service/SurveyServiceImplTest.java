package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.SurveyDao;
import ru.otus.homework.domain.Survey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceImplTest {

    private static final String FILE_NAME = "survey.csv";

    @Mock
    private SurveyDao surveyDao;

    @InjectMocks
    private SurveyServiceImpl surveyService;

    @DisplayName("Проверка метода getSurveyByName")
    @Test()
    public void getSurveyByName(){
        given(surveyDao.findSurveyByName(any()))
                .willReturn(new Survey(FILE_NAME));
        assertThat(surveyService.getSurveyByName(new Survey(FILE_NAME).getName())).isNotNull();
    }

    /*@DisplayName("Проверка метода getQuestions")
    @Test()
    public void checkMethodGetQuestions(){
        List<Question> questions = surveyService.getQuestions(new Survey(FILE_NAME));
        assertThat(questions).isNotEmpty();
    }*/
}
