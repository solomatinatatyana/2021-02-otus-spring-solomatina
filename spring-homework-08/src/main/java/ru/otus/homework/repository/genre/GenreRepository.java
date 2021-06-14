package ru.otus.homework.repository.genre;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre,Long> {
    Optional<Genre> findByName(String genre);
}
