package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.comment.CommentRepositoryJpa;
import ru.otus.homework.repository.comment.CommentRepositoryJpaImpl;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест класса CommentRepositoryJpaImpl должен ")
@DataJpaTest
@Import(CommentRepositoryJpaImpl.class)
public class CommentRepositoryJpaImplTest {
    private static final long EXISTING_FIRST_COMMENT_ID = 1L;
    private static final long EXISTING_SECOND_COMMENT_ID = 2L;
    private static final long EXISTING_THIRD_COMMENT_ID = 3L;
    private static final long EXISTING_FOURTH_COMMENT_ID = 4L;
    private static final long EXISTING_FIRST_BOOK_ID = 1L;
    private static final long EXISTING_SECOND_BOOK_ID = 2L;
    private static final long EXISTING_FIRST_AUTHOR_ID = 1L;
    private static final long EXISTING_FIRST_GENRE_ID = 1L;
    private static final long EXISTING_SECOND_AUTHOR_ID = 2L;
    private static final long EXISTING_SECOND_GENRE_ID = 2L;
    private static final String EXISTING_FIRST_BOOK = "The lord of the rings";
    private static final String EXISTING_SECOND_BOOK = "War and peace";
    private static final String EXISTING_FIRST_AUTHOR = "Tolkien";
    private static final String EXISTING_SECOND_AUTHOR = "Tolstoy";
    private static final String EXISTING_FIRST_GENRE = "Fantasy";
    private static final String EXISTING_SECOND_GENRE = "Roman";
    private static final String EXISTING_FIRST_COMMENT = "Great! rate 10";
    private static final String EXISTING_SECOND_COMMENT = "Great! rate 5";
    private static final String EXISTING_THIRD_COMMENT = "Super";
    private static final String EXISTING_FOURTH_COMMENT = "BAD BOOK";
    private Book book1;
    private Book book2;

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @BeforeEach()
    public void init(){
        book1 = new Book(EXISTING_FIRST_BOOK_ID, EXISTING_FIRST_BOOK,
                new Author(EXISTING_FIRST_AUTHOR_ID,EXISTING_FIRST_AUTHOR),
                new Genre(EXISTING_FIRST_GENRE_ID,EXISTING_FIRST_GENRE));
        book2 = new Book(EXISTING_SECOND_BOOK_ID, EXISTING_SECOND_BOOK,
                new Author(EXISTING_SECOND_AUTHOR_ID, EXISTING_SECOND_AUTHOR),
                new Genre(EXISTING_SECOND_GENRE_ID, EXISTING_SECOND_GENRE));
    }

    @DisplayName("проверять добавление нового комментария к книге в БД")
    @Test
    public void shouldInsertNewCommentToBook() {
        Comment expectedComment = new Comment(0, "testComment", book1);
        commentRepositoryJpa.save(expectedComment);
        Comment actualComment = commentRepositoryJpa.findById(expectedComment.getId()).get();
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(actualComment);
    }

    @DisplayName("проверять нахождение комментария книги по его id")
    @Test
    public void shouldReturnCommentById() {
        Comment expectedComment = em.find(Comment.class, EXISTING_FIRST_COMMENT_ID);
        Comment actualComment = commentRepositoryJpa.findById(expectedComment.getId()).get();
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("проверять нахождение всех комментриев")
    @Test
    public void shouldReturnExpectedCommentsList() {
        List<Comment> expectedCommentList = Arrays.asList(
                new Comment(EXISTING_FIRST_COMMENT_ID, EXISTING_FIRST_COMMENT,book1),
                new Comment(EXISTING_SECOND_COMMENT_ID, EXISTING_SECOND_COMMENT,book2),
                new Comment(EXISTING_THIRD_COMMENT_ID, EXISTING_THIRD_COMMENT,book1),
                new Comment(EXISTING_FOURTH_COMMENT_ID, EXISTING_FOURTH_COMMENT,book2)
        );
        List<Comment> actualCommentList = commentRepositoryJpa.findAll();
        assertThat(actualCommentList).isEqualTo(expectedCommentList);
    }

    @DisplayName("проверять удаление комментария по его id")
    @Test
    public void shouldDeleteCorrectCommentsById() {
        Comment secondComment = em.find(Comment.class, EXISTING_SECOND_COMMENT_ID);
        assertThat(secondComment).isNotNull();
        em.detach(secondComment);

        commentRepositoryJpa.deleteById(EXISTING_SECOND_COMMENT_ID);
        Comment deletedComment = em.find(Comment.class, EXISTING_SECOND_COMMENT_ID);

        assertThat(deletedComment).isNull();
    }
}
