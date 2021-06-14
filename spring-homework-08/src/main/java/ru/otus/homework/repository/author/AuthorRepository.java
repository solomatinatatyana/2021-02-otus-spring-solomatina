package ru.otus.homework.repository.author;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Author;

public interface AuthorRepository extends MongoRepository<Author,Long> {
}
