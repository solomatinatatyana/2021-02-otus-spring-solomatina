package ru.otus.homework.dao;

import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Survey;

import java.util.List;

public interface SurveyDao {
    Survey findSurveyByName(String name);
    List<Question> getQuestions(Survey survey);
}
