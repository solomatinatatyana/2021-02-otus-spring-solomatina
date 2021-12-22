package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.homework.repository.book.BookRepository;
import ru.otus.homework.util.RawResultPrinterImpl;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.homework.repository"})
@Import(RawResultPrinterImpl.class)
@DisplayName("Тест класса BookRepository должен ")
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;


    private static final String EXISTING_FIRST_GENRE = "Фантастика";
    private static final String EXISTING_SECOND_GENRE = "Роман";
    private static final String EXISTING_FIRST_BOOK = "Властелин колец";
    private static final String EXISTING_SECOND_BOOK = "Война и мир";
    private static final String EXISTING_FIRST_AUTHOR = "Джон Толкин";
    private static final String EXISTING_SECOND_AUTHOR = "Лев Толстой";

    private static final int BOOK_COUNT = 2;
    private static final int FIRST_BOOK_COMMENTS_COUNT = 2;

    /*@DisplayName("проверять добавление новой книги в БД")
    @Test
    public void shouldInsertNewBook() {
        Author author = new Author(1, EXISTING_SECOND_AUTHOR);
        Genre genre = new Genre(1, EXISTING_SECOND_GENRE);

        Book expectedBook = new Book(0, "testBook", author, genre);
        bookRepository.saveAndFlush(expectedBook);
        assertThat(expectedBook.getId()).isGreaterThan(0);

        Book actualBook = em.find(Book.class, expectedBook.getId());
        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().equals(""))
                .matches(s -> s.getAuthor() != null)
                .matches(s -> s.getGenre() != null);
    }*/

    /*@DisplayName("проверять нахождение книги по её id")
    @Test
    public void shouldReturnBookById() {
        Book expectedBook = em.find(Book.class, EXISTING_FIRST_BOOK_ID);
        Book actualBook = bookRepository.findById(expectedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }*/

    /*@DisplayName("проверять нахождение книги по её title")
    @Test
    public void shouldReturnBooKByTitle() {
        Book expectedBook = em.find(Book.class, EXISTING_FIRST_BOOK_ID);
        Book actualBook = bookRepository.findByTitle(expectedBook.getTitle()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }*/

    /*@DisplayName("проверять нахождение всех книг")
    @Test
    public void shouldReturnExpectedBooksList() {
        List<Book> expectedBookList = Arrays.asList(
                em.find(Book.class, EXISTING_FIRST_BOOK_ID),
                em.find(Book.class, EXISTING_SECOND_BOOK_ID)
        );
        List<Book> actualBookList = bookRepository.findAll();
        assertThat(actualBookList).containsAll(expectedBookList);
    }*/

   /* @DisplayName("проверять удаление книги по её id")
    @Test
    public void shouldDeleteCorrectBookById() {
        Book secondBook = em.find(Book.class, EXISTING_SECOND_BOOK_ID);
        assertThat(secondBook).isNotNull();
        em.detach(secondBook);

        bookRepository.deleteById(EXISTING_SECOND_BOOK_ID);
        Book deletedGenre = em.find(Book.class, EXISTING_SECOND_BOOK_ID);

        assertThat(deletedGenre).isNull();
    }*/

    /*@DisplayName("проверять удаление книги по её id с комментариями")
    @Test
    public void shouldDeleteCorrectBookByIdWithComments() {
        Book secondBook = em.find(Book.class, EXISTING_SECOND_BOOK_ID);
        Comment secondComment = em.find(Comment.class, EXISTING_SECOND_COMMENT_ID);
        assertThat(secondBook).isNotNull();
        em.detach(secondBook);
        em.detach(secondComment);

        bookRepository.deleteBookByIdWithComments(EXISTING_SECOND_BOOK_ID);
        Book deletedGenre = em.find(Book.class, EXISTING_SECOND_BOOK_ID);
        Book deletedComment = em.find(Book.class, EXISTING_SECOND_COMMENT_ID);

        assertThat(deletedGenre).isNull();
        assertThat(deletedComment).isNull();
    }*/

    /*@DisplayName("проверять удаление книги по её title")
    @Test
    public void shouldDeleteCorrectBookByTitle() {
        Book secondBook = em.find(Book.class, EXISTING_SECOND_BOOK_ID);
        assertThat(secondBook).isNotNull();
        em.detach(secondBook);

        bookRepository.deleteBookByTitle(EXISTING_SECOND_BOOK);
        Book deletedGenre = em.find(Book.class, EXISTING_SECOND_BOOK_ID);

        assertThat(deletedGenre).isNull();
    }*/

    /*@DisplayName("проверять удаление книги по её title с комментариями")
    @Test
    public void shouldDeleteCorrectBookByTitleWithComments() {
        Book secondBook = em.find(Book.class, EXISTING_SECOND_BOOK_ID);
        Comment secondComment = em.find(Comment.class, EXISTING_SECOND_COMMENT_ID);
        assertThat(secondBook).isNotNull();
        em.detach(secondBook);
        em.detach(secondComment);

        bookRepository.deleteBookByTitleWithComments(EXISTING_SECOND_BOOK);
        Book deletedGenre = em.find(Book.class, EXISTING_SECOND_BOOK_ID);
        Book deletedComment = em.find(Book.class, EXISTING_SECOND_COMMENT_ID);

        assertThat(deletedGenre).isNull();
        assertThat(deletedComment).isNull();
    }*/

    /*@DisplayName("проверять нахождение количества комметариев по книгам")
    @Test
    void shouldFindBookCommentsCount() {
        Book book1 = em.find(Book.class, EXISTING_FIRST_BOOK_ID);
        List<BookComments> bookComments = bookRepository.findBooksCommentsCount();
        assertThat(bookComments).hasSize(BOOK_COUNT)
                .contains(new BookComments(book1, FIRST_BOOK_COMMENTS_COUNT));
    }*/

    /*@DisplayName("проверять нахождение всех книг по автору")
    @Test
    void shouldFindBooksByAuthor() {
        List<Book> expectedBookList = Arrays.asList(
                em.find(Book.class, EXISTING_SECOND_BOOK_ID)
        );
        List<Book> actualBookList = bookRepository.findAllByAuthor_FullName(EXISTING_SECOND_AUTHOR);
        assertThat(actualBookList).containsAll(expectedBookList);
    }*/

    /*@DisplayName("проверять нахождение всех книг которые имеют жанр не равный заданному")
    @Test
    void shouldFindBooksByNotLikeGenre() {
        List<Book> expectedBookList = Arrays.asList(
                em.find(Book.class, EXISTING_SECOND_BOOK_ID)
        );
        List<Book> actualBookList = bookRepository.findAllByGenre_NameNotLike(EXISTING_FIRST_GENRE);
        assertThat(actualBookList).containsAll(expectedBookList);
    }*/

    /*@DisplayName("проверять нахождение всех книг у которых количество комментариев больше или равно заданному числу")
    @Test
    void shouldFindBooksCommentsByCommentCount() {
        List<BookComments> expectedBookList = Arrays.asList(
                new BookComments(new Book(EXISTING_FIRST_BOOK_ID, EXISTING_FIRST_BOOK,
                        new Author(1L, EXISTING_FIRST_AUTHOR),
                        new Genre(1L, EXISTING_FIRST_GENRE)),
                        2),
                new BookComments(new Book(EXISTING_SECOND_BOOK_ID, EXISTING_SECOND_BOOK,
                        new Author(2L, EXISTING_SECOND_AUTHOR),
                        new Genre(2L, EXISTING_SECOND_GENRE)),
                        2)
        );
        List<BookComments> actualBookList = bookRepository.findBooksByCountCommentsGreaterOrEqualsThan(1L);
        assertThat(CollectionUtils.isEqualCollection(actualBookList, expectedBookList));
    }*/
}
