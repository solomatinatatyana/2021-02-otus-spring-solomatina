package ru.otus.homework.repository.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,Long>, BookRepositoryCustom {
    List<Book> findAll();
    Optional<Book> findByTitle(String title);

    /*@Query("select new ru.otus.homework.dto.BookComments(b, count(c)) " +
            "from Book b left join b.comments c " +
            "group by b " +
            "order by count(c) desc ")
    List<BookComments> findBooksCommentsCount();*/

    void deleteBookByTitle(String title);

    List<Book> findAllByAuthor_FullName(String fio);

    List<Book> findAllByGenre_NameNotLike(String genre);

    /*@Query("select new ru.otus.homework.dto.BookComments(b, count(c)) " +
            "from Book b left join b.comments c group by b having count(c) >= :count")
    List<BookComments> findBooksByCountCommentsGreaterOrEqualsThan(@Param("count") long count);*/
}
