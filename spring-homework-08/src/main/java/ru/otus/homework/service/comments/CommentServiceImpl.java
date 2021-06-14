package ru.otus.homework.service.comments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Book;
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
    public void insertComment(long id, String commentText, long bookId) {
        commentRepository.save(new Comment(id, commentText, new Book(bookId)));
    }

    @Override
    public Comment getCommentById(long id) {
        return commentRepository.findById(id).orElseThrow(()->new CommentException("Comment with id [" + id + "] not found"));

    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteCommentById(long id) {
        commentRepository.deleteById(id);
    }
}
