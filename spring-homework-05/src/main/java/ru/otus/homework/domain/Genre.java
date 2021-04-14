package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public  class Genre {
    private  long id;
    private  String name;

    public Genre(long id) {
        this.id = id;
    }
}
