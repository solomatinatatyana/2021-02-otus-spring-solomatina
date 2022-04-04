package ru.otus.homework.rest;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Author;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.service.authors.AuthorService;

import java.util.List;

import static ru.otus.homework.metrics.Metrics.Authors.*;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @Timed(GET_AUTHORS_REQ_TIME)
    @GetMapping(value = "/author")
    public String getAuthors(Model model){
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "author-list";
    }

    @Timed(GET_AUTHOR_EDIT_REQ_TIME)
    @GetMapping(value = "/author/{id}/edit")
    public String editAuthor(@PathVariable("id") long id, Model model){
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author-edit";
    }

    @Timed(PATCH_AUTHOR_REQ_TIME)
    @PatchMapping(value = "/author/{id}")
    public String saveAuthor(@PathVariable("id") long id,
                             @ModelAttribute("author") AuthorDto authorDto){
        authorService.updateAuthorById(id, authorMapper.toAuthor(authorDto));
        return "redirect:/author";
    }

    @Timed(CREATE_AUTHOR_REQ_TIME)
    @PostMapping(value = "/author")
    public String addAuthor(@ModelAttribute("author") AuthorDto author){
        authorService.insertAuthor(authorMapper.toAuthor(author));
        return "redirect:/author";
    }

    @Timed(DELETE_AUTHOR_REQ_TIME)
    @DeleteMapping(value = "/author/{id}")
    public String deleteAuthor(@PathVariable("id") long id){
        authorService.deleteAuthorById(id);
        return "redirect:/author";
    }
}
