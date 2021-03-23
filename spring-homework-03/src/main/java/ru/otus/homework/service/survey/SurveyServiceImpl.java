package ru.otus.homework.service.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.SurveyConfig;
import ru.otus.homework.dao.SurveyDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.Survey;
import ru.otus.homework.exceptions.SurveyException;
import ru.otus.homework.service.io.ConsoleService;
import ru.otus.homework.service.locale.LocaleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {
    private List<String> gottenAnswers = new ArrayList<>();
    private int countCorrectAnswers;

    private final SurveyDao surveyDao;
    private final ConsoleService consoleService;
    private final LocaleService localeService;

    @Autowired
    public SurveyServiceImpl(SurveyConfig surveyConfig, SurveyDao surveyDao,
                             ConsoleService consoleService, LocaleService localeService) {
        this.countCorrectAnswers = surveyConfig.getCountCorrectAnswers();
        this.surveyDao = surveyDao;
        this.consoleService = consoleService;
        this.localeService = localeService;
    }

    @Override
    public String takeASurvey() throws IOException {
        consoleService.out(localeService.getLocalizationMessage("survey.get-info"));
        String firstName = consoleService.ask(localeService.getLocalizationMessage("survey.get-first-name"));
        String secondName = consoleService.ask(localeService.getLocalizationMessage("survey.get-second-name"));
        Student student = new Student(firstName,secondName);
        consoleService.out(student.toString());
        Survey survey = getSurveyByName(localeService.getLocalizationMessage("survey.name"));
        consoleService.out(localeService.getLocalizationMessage("survey.start"));
        consoleService.out("=================================");
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
        return consoleService.ask(localeService.getLocalizationMessage("survey.get-answer"));
    }

    private String checkAnswers(){
        List<String> correctAnswersList = getCorrectAnswers(localeService.getLocalizationMessage("survey.answers-file-name"));
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
        consoleService.out(localeService.getLocalizationMessage("survey.result.rating") + " ["+ correctAnswersCount +"]");
        if(correctAnswersCount >= countCorrectAnswers){
            result = localeService.getLocalizationMessage("survey.result.success");
        }else {
            result = localeService.getLocalizationMessage("survey.result.failed") + " [" + countCorrectAnswers + "]";
        }
        return result;
    }

}
