package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.service.comments.CommentService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommentApplicationCommands {
    private final CommentService commentService;

    private long id;
    private long book_id;
    private String commentText;

    @ShellMethod(value = "getting comment by id", key = {"getCommentById", "gcId"})
    public Comment getCommentById(@ShellOption long id){
        this.id = id;
        return commentService.getCommentById(id);
    }

    @ShellMethod(value = "get all comments", key = {"getAllComments", "gac"})
    public List<Comment> getAllComments(){
        return commentService.getAllComments();
    }

    @ShellMethod(value = "insert new comment", key = {"insertComment", "ic"})
    public void insertComment(@ShellOption long id,
                           @ShellOption String commentText,
                           @ShellOption long book_id) {
        this.id = id;
        this.commentText = commentText;
        this.book_id = book_id;
        commentService.insertComment(id, commentText, book_id);
    }

    @ShellMethod(value = "update comment text", key = {"updateComment", "ucId"})
    public void updateCommentTextById(@ShellOption long id, @ShellOption String commentText){
        commentService.updateCommentById(id, commentText);
    }

    @ShellMethod(value = "delete comment by id", key = {"deleteCommentById", "dcId"})
    public void deleteCommentById(@ShellOption long id){
        commentService.deleteCommentById(id);
    }

}
