package ru.otus.homework.service.comments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exceptions.CommentException;
import ru.otus.homework.repository.comment.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public void insertComment(Comment comment) {
        commentRepository.insert(comment);
    }

    @Override
    public Comment getCommentById(String id) {
        return commentRepository.findById(id).orElseThrow(()->new CommentException("Comment with id [" + id + "] not found"));
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteCommentById(String id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getBookCommentById(String bookId) {
        return commentRepository.findBookCommentById(bookId);
    }
}