package ru.otus.homework.service.authors;


import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.repository.author.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;
    private final MongoOperations mongoOperations;


    public AuthorServiceImpl(AuthorRepository authorRepository, MongoOperations mongoOperations) {
        this.authorRepository = authorRepository;
        this.mongoOperations = mongoOperations;
    }

    @Transactional
    @Override
    public void insertAuthor(Author author) {
        authorRepository.insert(author);
        System.out.println("Author ["+author.getFullName()+"] created successfully");
    }

    @Override
    public void updateAuthorById(String id, Author author) {
        Author authorToBeUpdated = getAuthorById(id);
        authorToBeUpdated.setFullName(author.getFullName());
        authorRepository.save(authorToBeUpdated);
    }

    @Override
    public Author getAuthorById(String id) {
        return authorRepository.findById(id).orElseThrow(()->new AuthorException("Author with id [" + id + "] not found"));
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.findByFullName(name).orElseThrow(()->new AuthorException("Author with name [" + name + "] not found"));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteAuthorById(String id) {
        authorRepository.deleteById(id);
    }
}
