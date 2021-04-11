package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exceptions.CommentException;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.repository.comment.CommentRepositoryJpa;
import ru.otus.homework.service.comments.CommentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@TestPropertySource("classpath:application.yaml")
@DisplayName("Сервис CommentService должен ")
@SpringBootTest
public class CommentServiceImplTest {

    @MockBean
    private CommentRepositoryJpa commentRepositoryJpa;
    @Autowired
    private CommentService commentService;


    @DisplayName("получать коммент книги по его id")
    @Test
    public void shouldReturnCommentById(){
        given(commentRepositoryJpa.findById(1)).willReturn(Optional.of(new Comment(1,"test",1)));
        Comment actualComment = commentService.getCommentById(1);
        assertThat(actualComment).isNotNull();
    }

    @DisplayName("получать все комменты")
    @Test
    public void shouldReturnAllComments(){
        List<Comment> expectedCommentList = Arrays.asList(
                new Comment(1,"testComment1",1),
                new Comment(2,"testComment2",2));
        given(commentRepositoryJpa.findAll()).willReturn(expectedCommentList);
        List<Comment> actualCommentList = commentService.getAllComments();
        assertThat(actualCommentList.equals(expectedCommentList));
    }

    @DisplayName("получать ошибку при запросе на несуществующий коммент")
    @Test
    public void shouldThrowCommentException(){
        Throwable exception = assertThrows(CommentException.class,()->{
            given(commentRepositoryJpa.findById(2)).willReturn(Optional.empty());
            commentService.getCommentById(2);
        });
        assertEquals("Comment with id [2] not found",exception.getMessage());
    }
}
