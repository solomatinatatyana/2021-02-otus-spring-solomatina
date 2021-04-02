package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Question;
import ru.otus.homework.exceptions.SurveyException;

import static ru.otus.homework.utils.CommonUtils.getBufferedReaderFromResource;

@Repository
public class ResourceDaoImpl implements ResourceDao {

    @Override
    public CSVReader getCsvSurvey(String fileName) {
        try{
            return new CSVReader(getBufferedReaderFromResource(fileName));
        }catch (NullPointerException e){
            throw new SurveyException("File with survey not found!");
        }
    }

    @Override
    public CsvToBean<Question> getCsvToBean(CSVReader csvReader){
        return new CsvToBeanBuilder(csvReader)
                .withType(Question.class)
                .withSeparator(',')
                .withIgnoreEmptyLine(true)
                .build();
    }
}
