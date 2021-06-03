package ru.otus.homework.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.rest.mappers.BookMapper;
import ru.otus.homework.rest.mappers.CommentMapper;
import ru.otus.homework.rest.mappers.GenreMapper;
import ru.otus.homework.service.authors.AuthorService;
import ru.otus.homework.service.books.BookService;
import ru.otus.homework.service.genres.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper bookMapper;
    private final GenreMapper genreMapper;
    private final AuthorMapper authorMapper;
    private final CommentMapper commentMapper;


    public BookController(BookService bookService, AuthorService authorService,
                          GenreService genreService, BookMapper bookMapper,
                          GenreMapper genreMapper, AuthorMapper authorMapper, CommentMapper commentMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookMapper = bookMapper;
        this.genreMapper = genreMapper;
        this.authorMapper = authorMapper;
        this.commentMapper = commentMapper;
    }

    @GetMapping(value = "/api/book")
    public List<BookDto> getBooks(@RequestParam(required = false, name = "author") String author,
                                  @RequestParam(required = false, name = "genre") String genre){
        List<BookDto> books;
        if(author!=null && !author.isEmpty()){
            books = bookService.getAllBooksWithGivenAuthor(author).stream().map(bookMapper::toBookDto).collect(Collectors.toList());
        }else if(genre!=null && !genre.isEmpty()){
            books = bookService.getAllBooksWithGivenGenre(genre).stream().map(bookMapper::toBookDto).collect(Collectors.toList());
        }else{
            books = bookService.getAllBooks().stream().map(bookMapper::toBookDto).collect(Collectors.toList());
        }
        books.forEach(b->{
            b.setComments(bookService.getCommentsByBookId(b.getId()).stream().map(commentMapper::toCommentDto).collect(Collectors.toList()));
            b.setAvgRating(bookService.getAvgRatingComments(b.getId()));
        });
        return books;
    }


    @PatchMapping(value = "/api/book/{id}")
    public ResponseEntity<BookDto> saveBook(@PathVariable("id") String id,
                           @RequestBody BookDto book){
        bookService.updateBookById(id, bookMapper.toBook(book));
        return ResponseEntity.ok(book);
    }

    @PostMapping(value = "/api/book")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto, BindingResult result){
        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bookDto);
        }
        Author author = authorService.getAuthorByName(bookDto.getAuthor().getFullName());
        Genre genre = genreService.getGenreByName(bookDto.getGenre().getName());
        bookService.createBook(bookMapper.toBook(bookDto), author,genre);
        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping(value = "/api/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") String id){
        bookService.deleteBookById(id);
        return ResponseEntity.ok("book with id ["+ id +"] has been deleted!");
    }

    @ExceptionHandler(BookException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") BookException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }
}
