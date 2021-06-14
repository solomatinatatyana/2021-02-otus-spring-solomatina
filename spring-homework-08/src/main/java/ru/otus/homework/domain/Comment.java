package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Id
    private long id;

    @Field(name = "comment_text")
    private String commentText;


    private Book book;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", Комментарий='" + commentText + '\'' +
                ", book=" + book +
                '}';
    }
}
