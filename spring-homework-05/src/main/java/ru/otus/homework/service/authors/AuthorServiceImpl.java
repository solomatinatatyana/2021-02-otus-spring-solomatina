package ru.otus.homework.service.authors;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.repository.author.AuthorRepositoryJpa;
import ru.otus.homework.domain.Author;
import ru.otus.homework.exceptions.AuthorException;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepositoryJpa authorRepositoryJpa;

    public AuthorServiceImpl(AuthorRepositoryJpa authorRepositoryJpa) {
        this.authorRepositoryJpa = authorRepositoryJpa;
    }


    @Transactional
    @Override
    public void insertAuthor(long id, String fio) {
        authorRepositoryJpa.insert(new Author(id, fio));
    }

    @Override
    public Author getAuthorById(long id) {
        return authorRepositoryJpa.findById(id).orElseThrow(()->new AuthorException("Author with id [" + id + "] not found"));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepositoryJpa.findAll();
    }

    @Transactional
    @Override
    public void deleteAuthorById(long id) {
        authorRepositoryJpa.deleteById(id);
    }
}
