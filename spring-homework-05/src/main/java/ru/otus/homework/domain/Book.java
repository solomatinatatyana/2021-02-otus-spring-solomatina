package ru.otus.homework.domain;

import lombok.Data;

@Data
public final class Book {
    private final long id;
    private final String title;
    private final long authorId;
    private final long genreId;
}
