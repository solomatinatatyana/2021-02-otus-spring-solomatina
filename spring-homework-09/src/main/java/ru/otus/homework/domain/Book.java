package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @DBRef
    private Author author;

    @DBRef
    private Genre genre;

    @Field(name = "comments")
    private List<Comment> comments;

    @Field(name = "avg_rating")
    private double avgRating;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title, Author author, Genre genre, Comment... comments) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = Arrays.asList(comments);
    }

    public Book(String id, String title, Author author, Genre genre, Comment... comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = Arrays.asList(comments);
    }


    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", comments=" + comments +
                '}';
    }
}
