package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.Config;
import ru.otus.homework.dao.SurveyDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.Survey;
import ru.otus.homework.exceptions.SurveyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService{
    private List<String> gottenAnswers = new ArrayList<>();
    private String surveyName;
    private String correctAnswersFileName;
    private int countCorrectAnswers;

    private final SurveyDao surveyDao;
    private final ConsoleService consoleService;

    @Autowired
    public SurveyServiceImpl(Config config, SurveyDao surveyDao, ConsoleService consoleService) {
        this.surveyName = config.getSurveyName();
        this.correctAnswersFileName = config.getCorrectAnswersFileName();
        this.countCorrectAnswers = config.getCountCorrectAnswers();
        this.surveyDao = surveyDao;
        this.consoleService = consoleService;
    }

    @Override
    public String takeASurvey() throws IOException {
        consoleService.out("Get your Info");
        String firstName = consoleService.ask("Input your first name: ");
        String secondName = consoleService.ask("Get your second name: ");
        Student student = new Student(firstName,secondName);
        consoleService.out(student.toString());
        Survey survey = getSurveyByName(surveyName);
        consoleService.out("Get started survey\n" +
                "=================================" +
                "");
        List<Question> questionList = getQuestions(survey);
        printQuestions(questionList);
        survey.setResult(checkAnswers());
        return survey.getResult();
    }

    @Override
    public Survey getSurveyByName(String surveyName) {
        return surveyDao.findSurveyByName(surveyName).orElseThrow(()->new SurveyException("Survey not found!"));
    }

    @Override
    public List<Question> getQuestions(Survey survey){
        return surveyDao.getQuestions(survey);
    }

    @Override
    public List<String> getCorrectAnswers(String fileName) {
        return surveyDao.findCorrectAnswers(fileName);
    }

    private void printQuestions(List<Question> list) throws IOException {
        for (Question question : list) {
            System.out.println(question.toString());
            printAnswers(question);
            gottenAnswers.add(getAnswer());
        }
    }

    private void printAnswers(Question question){
        List<Answer> answers = question.getAnswerList();
        System.out.println(answers);
    }

    private String getAnswer() throws IOException {
        return consoleService.ask("Input your answer: ");
    }

    private String checkAnswers(){
        List<String> correctAnswersList = getCorrectAnswers(correctAnswersFileName);
        int actualCorrectAnswersCount = 0;
            for(int i=0; i < gottenAnswers.size();i++){
                if(gottenAnswers.get(i).equals(correctAnswersList.get(i))){
                    actualCorrectAnswersCount++;
                }
            }
        return getResult(actualCorrectAnswersCount);
    }

    private String getResult(int correctAnswersCount){
        String result;
        System.out.println("Your rating is ["+ correctAnswersCount +"]");
        if(correctAnswersCount >= countCorrectAnswers){
            result = "Survey is passed!";
        }else {
            result = "Survey failed! Correct answers must be greater than [" + countCorrectAnswers + "]";
        }
        return result;
    }

}
