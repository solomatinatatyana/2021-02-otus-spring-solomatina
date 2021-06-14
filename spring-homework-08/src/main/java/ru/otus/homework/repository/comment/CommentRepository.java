package ru.otus.homework.repository.comment;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment,Long> {
    void deleteByBook_Id(long bookId);
    void deleteByBook_Title(String title);
}
