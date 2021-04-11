package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Author;
import ru.otus.homework.service.authors.AuthorService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AuthorApplicationCommands {
    private final AuthorService authorService;

    private long id;
    private String fio;

    @ShellMethod(value = "getting author by id", key = {"getAuthorById", "gaId"})
    public Author getAuthorById(@ShellOption long id){
        this.id = id;
        return authorService.getAuthorById(id);
    }

    @ShellMethod(value = "get all authors", key = {"getAllAuthors", "gaa"})
    public List<Author> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @ShellMethod(value = "insert new author", key = {"insertAuthor", "ia"})
    public void insertBook(@ShellOption long id,
                           @ShellOption String fio) {
        this.id = id;
        this.fio = fio;
        authorService.insertAuthor(id, fio);
    }

    @ShellMethod(value = "delete author by id", key = {"deleteAuthorById", "daId"})
    public void deleteBookById(@ShellOption long id){
        authorService.deleteAuthorById(id);
    }

}
