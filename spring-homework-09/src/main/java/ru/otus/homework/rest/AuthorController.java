package ru.otus.homework.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework.domain.Author;
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.service.authors.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @GetMapping(value = "/api/author")
    public List<AuthorDto> getAuthors(){
        return authorService.getAllAuthors().stream().map(authorMapper::toAuthorDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/api/author/{id}/edit")
    public ResponseEntity<AuthorDto> editAuthor(@PathVariable("id") String id){
        Author author = authorService.getAuthorById(id);
        if(author == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(authorMapper.toAuthorDto(author), HttpStatus.OK);
    }

    @PatchMapping(value = "/api/author/{id}")
    public ResponseEntity<AuthorDto> saveAuthor(@PathVariable("id") String id,
                             @RequestBody AuthorDto authorDto){
        authorService.updateAuthorById(id, authorMapper.toAuthor(authorDto));
        return ResponseEntity.ok(authorDto);
    }

    @PostMapping(value = "/api/author")
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto author){
        authorService.insertAuthor(authorMapper.toAuthor(author));
        return ResponseEntity.ok(author);
    }

    @DeleteMapping(value = "/api/author/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") String id){
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok("author with id ["+ id +"] deleted!");
    }

    @ExceptionHandler(AuthorException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") AuthorException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }
}
