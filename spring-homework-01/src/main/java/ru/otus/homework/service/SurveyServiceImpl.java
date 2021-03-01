package ru.otus.homework.service;

import ru.otus.homework.dao.SurveyDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Survey;

import java.util.List;

public class SurveyServiceImpl implements SurveyService{

    private final SurveyDao surveyDao;

    public SurveyServiceImpl(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }

    @Override
    public Survey getSurveyByName(String surveyName) {
        return surveyDao.findSurveyByName(surveyName);
    }

    @Override
    public void takeASurvey(Survey survey){
        List<Question> questionList = getQuestions(survey);
        printQuestions(questionList);
    }

    public void printQuestions(List<Question> list){
        for (Question question : list) {
            System.out.println(question.toString());
            printAnswers(question);
        }
    }

    private void printAnswers(Question question){
        List<Answer> answers = question.getAnswerList();
        System.out.println(answers);
    }

    @Override
    public List<Question> getQuestions(Survey survey){
        return surveyDao.getQuestions(survey);
    }
}
