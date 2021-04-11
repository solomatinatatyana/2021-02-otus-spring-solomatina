package ru.otus.homework.service.comments;

import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentService {
    void insertComment(long id, String commentText, long book_id);
    void updateCommentById(long id, String commentText);
    Comment getCommentById(long id);
    List<Comment> getAllComments();
    void deleteCommentById(long id);
}
