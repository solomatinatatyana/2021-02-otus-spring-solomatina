package ru.otus.homework.repository.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,String>, BookRepositoryCustom {

    List<Book> findAll();

    Optional<Book> findByTitle(String title);

    boolean existsBookByTitle(String title);
}
