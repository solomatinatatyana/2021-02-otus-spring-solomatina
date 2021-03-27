package ru.otus.homework.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import ru.otus.homework.utils.ListConverter;

import java.util.ArrayList;
import java.util.List;

@Data
public class Question {
    @CsvBindByName(column = "id")
    private String id;
    @CsvBindByName(column = "questionText")
    private String questionText;
    @CsvCustomBindByName(column = "answers", converter = ListConverter.class)
    private List<Answer> answerList = new ArrayList<>();

    public Question() { }

    public Question(String id, String questionText) {
        this.id = id;
        this.questionText = questionText;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", text='" + questionText + '\'' +
                '}';
    }
}
