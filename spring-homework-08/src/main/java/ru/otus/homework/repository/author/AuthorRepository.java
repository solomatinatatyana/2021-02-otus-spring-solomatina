package ru.otus.homework.repository.author;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author,String> {
    Optional<Author> findByFullName(String fio);
}
