package ru.otus.homework.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private String id;
    private String name;
    //private List<BookDto> books;

    public GenreDto(String name) {
        this.name = name;
    }
}
