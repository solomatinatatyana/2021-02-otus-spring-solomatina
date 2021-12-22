package ru.otus.homework.repository.book;

import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookComments;

import java.util.List;

public interface BookRepositoryCustom {
    List<Book> findAllByAuthor_FullName(String fio);
    List<Book> findAllByGenre_Name(String genre);
    List<BookComments> getBookCommentsByBookId(String bookId);
    void removeCommentsArrayElementsById(String id);
    void deleteBookByIdWithComments(String id);
}
