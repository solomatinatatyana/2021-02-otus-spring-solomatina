package ru.otus.homework.repository.book;

public interface BookRepositoryCustom {
    void deleteBookByIdWithComments(long id);
    void deleteBookByTitleWithComments(String title);
}
