package ru.otus.homework.service.authors;


import org.springframework.stereotype.Service;
import ru.otus.homework.dao.author.AuthorDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.exceptions.AuthorException;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }


    @Override
    public void insertAuthor(long id, String fio) {
        authorDao.insert(new Author(id, fio));
    }

    @Override
    public Author getAuthorById(long id) {
        return authorDao.findById(id).orElseThrow(()->new AuthorException("Author with id [" + id + "] not found"));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }

    @Override
    public void deleteAuthorById(long id) {
        authorDao.deleteById(id);
    }
}
