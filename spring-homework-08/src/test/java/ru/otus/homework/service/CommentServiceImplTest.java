package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.CommentException;
import ru.otus.homework.repository.comment.CommentRepository;
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
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;


    @DisplayName("получать коммент книги по его id")
    @Test
    public void shouldReturnCommentById(){
        given(commentRepository.findById(1L))
                .willReturn(Optional.of(
                        new Comment(1L,"test",
                                new Book(1, "testBook",
                                        new Author(1L, "testAuthor"),
                                        new Genre(1L, "testGenre")))));
        Comment actualComment = commentService.getCommentById(1L);
        assertThat(actualComment).isNotNull();
    }

    @DisplayName("получать все комменты")
    @Test
    public void shouldReturnAllComments(){
        List<Comment> expectedCommentList = Arrays.asList(
                new Comment(1,"testComment1",
                        new Book(1, "testBook",
                                new Author(1L, "testAuthor"),
                                new Genre(1L, "testGenre"))),
                new Comment(2,"testComment2",
                        new Book(2, "testBook",
                                new Author(2L, "testAuthor2"),
                                new Genre(2L, "testGenre2"))));
        given(commentRepository.findAll()).willReturn(expectedCommentList);
        Iterable<Comment> actualCommentList = commentService.getAllComments();
        assertThat(actualCommentList.equals(expectedCommentList));
    }

    @DisplayName("получать ошибку при запросе на несуществующий коммент")
    @Test
    public void shouldThrowCommentException(){
        Throwable exception = assertThrows(CommentException.class,()->{
            given(commentRepository.findById(2L)).willReturn(Optional.empty());
            commentService.getCommentById(2);
        });
        assertEquals("Comment with id [2] not found",exception.getMessage());
    }
}
