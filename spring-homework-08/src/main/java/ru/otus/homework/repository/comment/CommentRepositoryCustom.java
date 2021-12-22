package ru.otus.homework.repository.comment;

import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findBookCommentById(String bookId);
}
