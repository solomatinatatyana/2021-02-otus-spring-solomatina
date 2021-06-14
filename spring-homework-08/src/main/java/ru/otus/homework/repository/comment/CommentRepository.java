package ru.otus.homework.repository.comment;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment,Long> {
    void deleteByBook_Id(long bookId);
    void deleteByBook_Title(String title);
}
