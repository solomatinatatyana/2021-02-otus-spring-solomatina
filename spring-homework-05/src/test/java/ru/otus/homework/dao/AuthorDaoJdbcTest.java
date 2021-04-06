package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.dao.author.AuthorDaoJdbc;
import ru.otus.homework.domain.Author;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тест класса AuthorDaoJdbc должен ")
@JdbcTest
@Import(AuthorDaoJdbc.class)
public class AuthorDaoJdbcTest {

    private static final int EXISTING_FIRST_AUTHOR_ID = 1;
    private static final String EXISTING_FIRST_AUTHOR_FIO = "Tolkien";
    private static final int EXISTING_SECOND_AUTHOR_ID = 2;
    private static final String EXISTING_SECOND_AUTHOR_FIO = "Tolstoy";


    @Autowired
    private AuthorDaoJdbc authorDao;

    @DisplayName("проверять добавление нового автора книги в БД")
    @Test
    public void shouldInsertNewAuthor() {
        Author expectedAuthor = new Author(3, "testAuthor");
        authorDao.insert(expectedAuthor);
        Author actualAuthor = authorDao.findById(expectedAuthor.getId()).get();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("проверять нахождение автора книги по его id")
    @Test
    public void shouldReturnAuthorById() {
        Author expectedAuthor = new Author(EXISTING_FIRST_AUTHOR_ID, EXISTING_FIRST_AUTHOR_FIO);
        Author actualAuthor = authorDao.findById(expectedAuthor.getId()).get();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
   }

    @DisplayName("проверять нахождение всех авторов")
    @Test
    public void shouldReturnExpectedAuthorsList() {
        List<Author> expectedAuthorList = Arrays.asList(
                new Author(EXISTING_FIRST_AUTHOR_ID, EXISTING_FIRST_AUTHOR_FIO),
                new Author(EXISTING_SECOND_AUTHOR_ID, EXISTING_SECOND_AUTHOR_FIO)
        );
        List<Author> actualAuthorList = authorDao.findAll();
        assertThat(actualAuthorList).containsAll(expectedAuthorList);
    }

    @DisplayName("проверять удаление автора по его id")
    @Test
    public void shouldDeleteCorrectAuthorById() {
        assertThatCode(() -> authorDao.findById(EXISTING_SECOND_AUTHOR_ID))
                .doesNotThrowAnyException();

        authorDao.deleteById(EXISTING_SECOND_AUTHOR_ID);

        assertThatThrownBy(() -> authorDao.findById(EXISTING_SECOND_AUTHOR_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }


}
