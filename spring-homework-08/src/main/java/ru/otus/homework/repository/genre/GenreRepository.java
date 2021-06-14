package ru.otus.homework.repository.genre;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre,Long> {
    Optional<Genre> findByName(String genre);
}
