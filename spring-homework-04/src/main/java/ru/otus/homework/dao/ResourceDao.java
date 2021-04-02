package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import ru.otus.homework.domain.Question;

public interface ResourceDao {
    CSVReader getCsvSurvey(String fileName);
    CsvToBean<Question> getCsvToBean(CSVReader csvReader);
}
