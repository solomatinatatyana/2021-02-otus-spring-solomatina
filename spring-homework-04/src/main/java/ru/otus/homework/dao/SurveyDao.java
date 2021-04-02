package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Survey;

import java.util.List;
import java.util.Optional;

public interface SurveyDao {
    Optional<Survey> findSurveyByName(String surveyName);
    List<Question> getQuestions(Survey survey);
    List<String> findCorrectAnswers(String fileName);
    CSVReader getCsvSurvey(String fileName);
    CsvToBean<Question> getCsvToBean(CSVReader csvReader);
}
