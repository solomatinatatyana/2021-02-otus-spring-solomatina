package ru.otus.homework.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private long id;
    private String name;
    private List<BookDto> books;

    public GenreDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
