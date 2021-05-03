package ru.otus.homework.repository.book;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookComments;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long>, BookRepositoryCustom {
    @EntityGraph(value = "book-entity-graph")
    List<Book> findAll();
    Optional<Book> findByTitle(String title);

    @Query("select new ru.otus.homework.dto.BookComments(b, count(c)) " +
            "from Book b left join b.comments c " +
            "group by b " +
            "order by count(c) desc ")
    List<BookComments> findBooksCommentsCount();

    void deleteBookByTitle(String title);

    @EntityGraph(value = "book-entity-graph")
    List<Book> findAllByAuthor_FullName(String fio);

    @EntityGraph(value = "book-entity-graph")
    List<Book> findAllByGenre_NameNotLike(String genre);

    @Query("select new ru.otus.homework.dto.BookComments(b, count(c)) " +
            "from Book b left join b.comments c group by b having count(c) >= :count")
    List<BookComments> findBooksByCountCommentsGreaterOrEqualsThan(@Param("count") long count);
}
