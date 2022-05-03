package ru.otus.homework.service.authors;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.repository.author.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.homework.util.SleepUtil.sleepRandomly;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;


    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @HystrixCommand(commandKey="getAuthorKey", fallbackMethod="buildFallbackAuthorsCreate")
    @Transactional
    @Override
    public void insertAuthor(Author author) {
        sleepRandomly();
        authorRepository.saveAndFlush(author);
    }

    @HystrixCommand(commandKey="getAuthorKey", fallbackMethod="buildFallbackAuthorsUpdate")
    @Override
    public void updateAuthorById(long id, Author author) {
        sleepRandomly();
        Author authorToBeUpdated = getAuthorById(id);
        authorToBeUpdated.setFullName(author.getFullName());
        authorRepository.saveAndFlush(authorToBeUpdated);
    }

    @HystrixCommand(commandKey="getAuthorKey", fallbackMethod="buildFallbackAuthorsById")
    @Override
    public Author getAuthorById(long id) {
        sleepRandomly();
        return authorRepository.findById(id).orElseThrow(()->new AuthorException("Author with id [" + id + "] not found"));
    }

    @HystrixCommand(commandKey="getAuthorKey", fallbackMethod="buildFallbackAuthors")
    @Override
    public Author getAuthorByName(String name) {
        sleepRandomly();
        return authorRepository.findByFullName(name).orElseThrow(()->new AuthorException("Author with name [" + name + "] not found"));
    }

    @HystrixCommand(commandKey="getAuthorKey", fallbackMethod="buildFallbackAllAuthors")
    @Override
    public List<Author> getAllAuthors() {
        sleepRandomly();
        return authorRepository.findAll();
    }

    @HystrixCommand(commandKey="getAuthorKey", fallbackMethod="buildFallbackAuthorDelete")
    @Transactional
    @Override
    public void deleteAuthorById(long id) {
        sleepRandomly();
        authorRepository.deleteById(id);
    }

    public Author buildFallbackAuthors(String authorName) {
        Author author = new Author();
        author.setId(0L);
        author.setFullName(authorName);
        return author;
    }

    public Author buildFallbackAuthorsById(long id) {
        Author author = new Author();
        author.setId(id);
        author.setFullName("N/A author");
        return author;
    }

    public List<Author> buildFallbackAllAuthors() {
        Author author = new Author();
        author.setId(0L);
        author.setFullName("N/A author");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        return authors;
    }

    public void buildFallbackAuthorsUpdate(long id, Author author) {
        System.out.println(author.getFullName() + " not updated");
    }

    public void buildFallbackAuthorsCreate(Author author) {
        System.out.println(author.getFullName() + " not created");
    }

    public void buildFallbackAuthorDelete(long id) {
        System.out.println("author with " + id + " not deleted");
    }

}
