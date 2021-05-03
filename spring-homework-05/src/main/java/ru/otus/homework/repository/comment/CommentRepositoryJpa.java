package ru.otus.homework.repository.comment;

import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa {
    Comment save(Comment comment);
    Optional<Comment> findById(long id);
    List<Comment> findAll();
    void deleteById(long id);
}
