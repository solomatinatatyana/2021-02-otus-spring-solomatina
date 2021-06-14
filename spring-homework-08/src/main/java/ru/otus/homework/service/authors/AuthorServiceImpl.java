package ru.otus.homework.service.authors;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.repository.author.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;


    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Override
    public void insertAuthor(long id, String fio) {
        authorRepository.save(new Author(id, fio));
    }

    @Override
    public Author getAuthorById(long id) {
        return authorRepository.findById(id).orElseThrow(()->new AuthorException("Author with id [" + id + "] not found"));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteAuthorById(long id) {
        authorRepository.deleteById(id);
    }
}
