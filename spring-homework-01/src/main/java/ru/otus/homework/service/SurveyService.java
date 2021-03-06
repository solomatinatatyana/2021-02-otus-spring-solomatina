package ru.otus.homework.service;

import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Survey;

import java.io.IOException;
import java.util.List;

public interface SurveyService {
    Survey getSurveyByName(String surveyName);
    String takeASurvey() throws IOException;
    List<Question> getQuestions(Survey survey);
    List<String> getCorrectAnswers(String fileName);
}
