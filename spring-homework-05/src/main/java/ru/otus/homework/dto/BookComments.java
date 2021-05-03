package ru.otus.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.domain.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookComments {
    private Book book;
    private long CommentsCount;
}
