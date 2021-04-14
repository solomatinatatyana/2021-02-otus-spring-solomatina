package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Book {
    private long id;
    private String title;
    private Author author;
    private Genre genre;

    @Override
    public String toString() {
        return "id="+ id +
                ", Название='" + title + '\'' +
                ", автор=" + author.getFullName() +
                ", Жанр=" + genre.getName();
    }
}
