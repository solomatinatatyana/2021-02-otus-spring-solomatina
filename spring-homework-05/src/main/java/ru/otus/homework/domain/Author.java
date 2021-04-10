package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Author {
    private  long id;
    private  String fullName;

    public Author(long id) {
        this.id = id;
    }
}
