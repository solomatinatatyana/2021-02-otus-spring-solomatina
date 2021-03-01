package ru.otus.homework.service;

import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Survey;

import java.io.IOException;
import java.util.List;

public interface SurveyService {
    Survey getSurveyByName(String surveyName);
    void takeASurvey(Survey survey) throws IOException;
    List<Question> getQuestions(Survey survey);
}
