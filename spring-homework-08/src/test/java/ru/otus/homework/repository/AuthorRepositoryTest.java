package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Author;
import ru.otus.homework.repository.author.AuthorRepository;
import ru.otus.homework.util.RawResultPrinterImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.homework.repository"})
@Import(RawResultPrinterImpl.class)
@DisplayName("Тест класса AuthorRepository должен ")
public class AuthorRepositoryTest {

    private static final String EXISTING_FIRST_AUTHOR_FIO = "Джон Толкин";
    private static final String EXISTING_SECOND_AUTHOR_FIO = "Лев Толстой";

    @Autowired
    private AuthorRepository authorRepository;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("проверять добавление нового автора книги в БД")
    @Test
    public void shouldInsertNewAuthor() {
        Author expectedAuthor = new Author("testAuthor");
        authorRepository.save(expectedAuthor);
        assertThat(expectedAuthor.getId()).isNotBlank();
        Author actualAuthor = authorRepository.findById(expectedAuthor.getId()).get();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("проверять нахождение автора книги по его id")
    @Test
    public void shouldReturnAuthorById() {
        List<Author> authors = authorRepository.findAll();
        Author expectedAuthor = authors.get(0);
        Author actualAuthor = authorRepository.findById(expectedAuthor.getId()).get();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
   }

    @DisplayName("проверять нахождение всех авторов")
    @Test
    public void shouldReturnExpectedAuthorsList() {
        List<String> expectedAuthorFioList = Arrays.asList(
                new Author(EXISTING_FIRST_AUTHOR_FIO).getFullName(),
                new Author(EXISTING_SECOND_AUTHOR_FIO).getFullName()
        );
        List<Author> actualAuthorList = authorRepository.findAll();
        List<String> actualAuthorFioList = new ArrayList<>();
        actualAuthorList.forEach(author -> actualAuthorFioList.add(author.getFullName()));
        assertThat(actualAuthorFioList).containsAll(expectedAuthorFioList);
    }

    @DisplayName("проверять удаление автора по его id")
    @Test
    public void shouldDeleteCorrectAuthorById() {
        List<Author> authorsBeforeDeleting = authorRepository.findAll();
        int countBeforeDeleting = authorsBeforeDeleting.size();

        Author deletingAuthor = authorsBeforeDeleting.get(0);
        assertThat(deletingAuthor).isNotNull();

        authorRepository.deleteById(deletingAuthor.getId());
        List<Author> authorsAfterDeleting = authorRepository.findAll();
        int countAfterDeleting = authorsAfterDeleting.size();

        assertThat(countBeforeDeleting).isNotEqualTo(countAfterDeleting);
        assertThat(deletingAuthor).isNotIn(authorsAfterDeleting);
    }
}
