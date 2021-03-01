package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Survey;
import ru.otus.homework.service.SurveyServiceImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class SurveyDaoImpl implements SurveyDao {
    private final Survey survey;

    public SurveyDaoImpl(Survey survey) {
        this.survey = survey;
    }

    @Override
    public Survey findSurveyByName(String surveyName) {
        return new Survey(surveyName);
    }

    @Override
    public List<Question> getQuestions(Survey survey) {
        InputStream resource = SurveyServiceImpl.class.getClassLoader().getResourceAsStream(survey.getName());
        assert resource != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource));
        CSVReader reader = new CSVReader(bufferedReader);
        CsvToBean<Question> csvReader = new CsvToBeanBuilder(reader)
                .withType(Question.class)
                .withSeparator(',')
                .withIgnoreEmptyLine(true)
                .build();
        return csvReader.parse();
    }
}
