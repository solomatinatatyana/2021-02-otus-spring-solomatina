package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Question;
import ru.otus.homework.exceptions.SurveyException;

import java.io.BufferedReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SurveyDaoImplTest {

    @Mock
    private ResourceDao resourceDao;
    @Mock
    private BufferedReader bufferedReader;
    @Mock
    private CSVReader csvReader;

    @InjectMocks
    private SurveyDaoImpl surveyDao;

    @DisplayName("Проверка выбрасывания исключения SurveyException")
    @Test()
    public void isShouldThrowSurveyException(){
        Throwable exception = assertThrows(SurveyException.class,()->{
            throw new SurveyException("SurveyExceptionTest");
        });
        assertEquals("SurveyExceptionTest",exception.getMessage());
    }

    @DisplayName("Проверка метода getCsvSurvey")
    @Test()
    public void getCsvSurvey(){
        given(resourceDao.getCsvSurvey("fileName")).willReturn(new CSVReader(bufferedReader));
        CSVReader reader = surveyDao.getCsvSurvey("fileName");
        assertThat(reader).isNotNull();
    }

    @DisplayName("Проверка метода getCsvToBean")
    @Test()
    public void getCsvToBean(){
        given(resourceDao.getCsvToBean(csvReader)).willReturn(new CsvToBean<>());
        CsvToBean<Question> csvBeans = surveyDao.getCsvToBean(csvReader);
        assertThat(csvBeans).isNotNull();
    }
}
