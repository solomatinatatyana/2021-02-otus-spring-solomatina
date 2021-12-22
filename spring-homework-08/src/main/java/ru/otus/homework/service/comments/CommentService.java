package ru.otus.homework.service.comments;

import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentService {
    void insertComment(Comment comment);
    Comment getCommentById(String id);
    List<Comment> getAllComments();
    void deleteCommentById(String id);
    List<Comment> getBookCommentById(String bookId);
}
