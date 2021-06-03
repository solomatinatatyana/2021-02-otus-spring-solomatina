package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    @Field(name = "comment_text")
    private String commentText;

    @Field(name = "rating")
    private Integer rating;

    public Comment(String commentText) {
        this.commentText = commentText;
    }

    public Comment(String commentText, Integer rating) {
        this.commentText = commentText;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", commentText='" + commentText + '\'' +
                ", rating=" + rating +
                '}';
    }
}
