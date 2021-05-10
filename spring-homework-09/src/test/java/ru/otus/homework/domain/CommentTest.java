package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Comment")
public class CommentTest {
    @DisplayName("Комментарий корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectCommentConstructor(){
        Book book = new Book(1,"testBook",
                new Author(1,"testAuthor"),
                new Genre(1, "testGenre"));
        Comment comment = new Comment(1, "testComment", book);
        assertAll("comment",
                ()-> assertEquals(1, comment.getId()),
                ()-> assertEquals("testComment", comment.getCommentText()),
                ()->assertEquals(book, comment.getBook()));
    }
}
