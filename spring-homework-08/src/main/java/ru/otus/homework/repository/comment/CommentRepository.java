package ru.otus.homework.repository.comment;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String>, CommentRepositoryCustom {
    //void deleteByBook_Id(String bookId);
    //void deleteByBook_Title(String title);
    //void deleteAllById(List<Comment> commentIds);
}
