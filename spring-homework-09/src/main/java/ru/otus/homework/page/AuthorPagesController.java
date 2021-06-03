package ru.otus.homework.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.homework.domain.Author;
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.service.authors.AuthorService;

@Controller
public class AuthorPagesController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorPagesController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @GetMapping(value = "/author")
    public String getAuthors(Model model){
        model.addAttribute("authors", "authors");
        return "author-list";
    }

    @GetMapping(value = "/author/{id}/edit")
    public String editAuthor(@PathVariable("id") String id, Model model){
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author-edit";
    }
}
