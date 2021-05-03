package ru.otus.homework.repository.book;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework.repository.comment.CommentRepository;

public class BookRepositoryImpl implements BookRepositoryCustom{

    @Autowired
    private BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public BookRepositoryImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void deleteBookByIdWithComments(long id) {
        commentRepository.deleteByBook_Id(id);
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteBookByTitleWithComments(String title) {
        commentRepository.deleteByBook_Title(title);
        bookRepository.deleteBookByTitle(title);
    }
}
