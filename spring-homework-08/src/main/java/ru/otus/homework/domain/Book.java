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
@Document(collection = "books")
public class Book {
    @Id
    private long id;

    @Field(name = "title")
    private String title;

    @DBRef
    private Author author;

    @DBRef
    private Genre genre;

    private List<Comment> comments;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id="+ id +
                ", Название='" + title + '\'' +
                ", автор=" + author.getFullName() +
                ", Жанр=" + genre.getName()+"}";
    }
}
