package ru.otus.homework.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private long id;
    @NotBlank(message = "title must not be blank")
    private String title;
    private AuthorDto author;
    private GenreDto genre;
    private List<CommentDto> comments;

    public BookDto(long id) {
        this.id = id;
    }

    public BookDto(long id, @NotBlank(message = "title must not be blank") String title) {
        this.id = id;
        this.title = title;
    }
}
