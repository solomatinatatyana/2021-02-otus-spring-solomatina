package ru.otus.homework.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.otus.homework.utils.ListConverter;

import java.util.ArrayList;
import java.util.List;

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

    public String getQuestionText() {
        return questionText;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", text='" + questionText + '\'' +
                '}';
    }
}
