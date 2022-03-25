package ru.otus.homework.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Author;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.service.authors.AuthorService;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @GetMapping(value = "/author")
    public String getAuthors(Model model){
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "author-list";
    }

    @GetMapping(value = "/author/{id}/edit")
    public String editAuthor(@PathVariable("id") long id, Model model){
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author-edit";
    }

    @PatchMapping(value = "/author/{id}")
    public String saveAuthor(@PathVariable("id") long id,
                             @ModelAttribute("author") AuthorDto authorDto){
        authorService.updateAuthorById(id, authorMapper.toAuthor(authorDto));
        return "redirect:/author";
    }

    @PostMapping(value = "/author")
    public String addAuthor(@ModelAttribute("author") AuthorDto author){
        authorService.insertAuthor(authorMapper.toAuthor(author));
        return "redirect:/author";
    }

    @DeleteMapping(value = "/author/{id}")
    public String deleteAuthor(@PathVariable("id") long id){
        authorService.deleteAuthorById(id);
        return "redirect:/author";
    }
}
