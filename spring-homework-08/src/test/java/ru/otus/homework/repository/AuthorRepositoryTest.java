package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework.domain.Author;
import ru.otus.homework.repository.author.AuthorRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест класса AuthorRepository должен ")
@DataJpaTest
public class AuthorRepositoryTest {

    private static final long EXISTING_FIRST_AUTHOR_ID = 1L;
    private static final String EXISTING_FIRST_AUTHOR_FIO = "Tolkien";
    private static final long EXISTING_SECOND_AUTHOR_ID = 2L;
    private static final String EXISTING_SECOND_AUTHOR_FIO = "Tolstoy";

    @Autowired
    private AuthorRepository authorDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("проверять добавление нового автора книги в БД")
    @Test
    public void shouldInsertNewAuthor() {
        Author expectedAuthor = new Author(3, "testAuthor");
        authorDao.save(expectedAuthor);
        assertThat(expectedAuthor.getId()).isGreaterThan(0);
        Author actualAuthor = authorDao.findById(expectedAuthor.getId()).get();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("проверять нахождение автора книги по его id")
    @Test
    public void shouldReturnAuthorById() {
        Author expectedAuthor = em.find(Author.class, EXISTING_FIRST_AUTHOR_ID);
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
        Iterable<Author> actualAuthorList = authorDao.findAll();
        assertThat(actualAuthorList).containsAll(expectedAuthorList);
    }

    @DisplayName("проверять удаление автора по его id")
    @Test
    public void shouldDeleteCorrectAuthorById() {
        Author secondAuthor = em.find(Author.class, EXISTING_SECOND_AUTHOR_ID);
        assertThat(secondAuthor).isNotNull();
        em.detach(secondAuthor);

        authorDao.deleteById(EXISTING_SECOND_AUTHOR_ID);
        Author deletedAuthor = em.find(Author.class, EXISTING_SECOND_AUTHOR_ID);

        assertThat(deletedAuthor).isNull();
    }


}
