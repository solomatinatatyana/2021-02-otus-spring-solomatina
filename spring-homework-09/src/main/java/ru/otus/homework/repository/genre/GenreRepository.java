package ru.otus.homework.repository.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre,Long> {
    Optional<Genre> findByName(String genre);
}
