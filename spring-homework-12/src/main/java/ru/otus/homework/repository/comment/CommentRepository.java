package ru.otus.homework.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    void deleteByBook_Id(long bookId);
    void deleteByBook_Title(String title);
}
