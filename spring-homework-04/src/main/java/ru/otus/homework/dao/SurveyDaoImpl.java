package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Survey;
import ru.otus.homework.exceptions.SurveyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.otus.homework.utils.CommonUtils.getBufferedReaderFromResource;

@Repository
public class SurveyDaoImpl implements SurveyDao {

    private final ResourceDao resourceDao;

    public SurveyDaoImpl(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }

    @Override
    public Optional<Survey> findSurveyByName(String surveyName) {
        return Optional.of(new Survey(surveyName));
    }

    @Override
    public List<Question> getQuestions(Survey survey) {
        return getCsvToBean(getCsvSurvey(survey.getName())).parse();
    }

    @Override
    public List<String> findCorrectAnswers(String filename) {
        List<String> correctAnswersList = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = getBufferedReaderFromResource(filename);
            while ((line=reader.readLine())!=null){
                if(!line.isEmpty()){
                    correctAnswersList.add(line);
                }
            }
        }catch (NullPointerException | IOException e){
            throw new SurveyException("File with correct answers not found!");
        }
        return correctAnswersList;
    }

    @Override
    public CSVReader getCsvSurvey(String fileName) {
        return resourceDao.getCsvSurvey(fileName);
    }

    @Override
    public CsvToBean<Question> getCsvToBean(CSVReader csvReader) {
        return resourceDao.getCsvToBean(csvReader);
    }
}
