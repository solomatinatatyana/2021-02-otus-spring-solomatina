package ru.otus.homework.repository.author;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author> findByFullName(String fio);
}
