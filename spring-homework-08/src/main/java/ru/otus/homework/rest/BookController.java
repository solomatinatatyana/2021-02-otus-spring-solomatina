package ru.otus.homework.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.rest.dto.GenreDto;
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.rest.mappers.BookMapper;
import ru.otus.homework.rest.mappers.GenreMapper;
import ru.otus.homework.service.authors.AuthorService;
import ru.otus.homework.service.books.BookService;
import ru.otus.homework.service.genres.GenreService;

import javax.validation.Valid;
import java.util.List;

@Controller
//@Import(MongoCommentsCascadeDeleteEventsListener.class)
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper bookMapper;
    private final GenreMapper genreMapper;
    private final AuthorMapper authorMapper;


    public BookController(BookService bookService, AuthorService authorService,
                          GenreService genreService, BookMapper bookMapper,
                          GenreMapper genreMapper, AuthorMapper authorMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookMapper = bookMapper;
        this.genreMapper = genreMapper;
        this.authorMapper = authorMapper;
    }

    @GetMapping(value = "/book")
    public String getBooks(Model model){
        List<Book> books = bookService.getAllBooks();
        books.forEach(b->b.setAvgRating(bookService.getAvgRatingComments(b.getId())));
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping(value = "/book/filter")
    public String filterBooks(@RequestParam("author") String author,
                              @RequestParam("genre") String genre, Model model){
        List<Book> books;
        if(author!=null && !author.isEmpty()){
            books = bookService.getAllBooksWithGivenAuthor(author);
        }else if(genre!=null && !genre.isEmpty()){
            books = bookService.getAllBooksWithGivenGenre(genre);
        }else{
            books = bookService.getAllBooks();
        }
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping(value = "/book/{id}/edit")
    public String editBookForm(@PathVariable("id") String id, Model model){
        BookDto book = bookMapper.toBookDto(bookService.getBookById(id));
        List<GenreDto> genreList = genreMapper.toGenreDtoList(genreService.getAllGenres());
        List<AuthorDto> authorList = authorMapper.toAuthorDtoList(authorService.getAllAuthors());
        model.addAttribute("book",book);
        model.addAttribute("genres",genreList);
        model.addAttribute("authors",authorList);
        return "book-edit";
    }

    @PatchMapping(value = "/book/{id}")
    public String saveBook(@PathVariable("id") String id,
                           @ModelAttribute("book") BookDto book){
        bookService.updateBookById(id, bookMapper.toBook(book));
        return "redirect:/book";
    }

    @GetMapping(value = "/book/add")
    public String showAddBookForm(Model model){
        model.addAttribute("book",new Book());
        return "book-add";
    }

    @PostMapping(value = "/book")
    public String addBook(@Valid @ModelAttribute("book") BookDto book, BindingResult result, String fio, String name, Model model){
        if(result.hasErrors()) {
            return "book-add";
        }
        Author author = authorService.getAuthorByName(fio);
        Genre genre = genreService.getGenreByName(name);
        bookService.createBook(bookMapper.toBook(book), author,genre);
        return "redirect:/book";
    }

    @DeleteMapping(value = "/book/{id}")
    public String deleteBook(@PathVariable("id") String id){
        bookService.deleteBookById(id);
        return "redirect:/book";
    }

    @ExceptionHandler(BookException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") BookException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }

    @ExceptionHandler(GenreException.class)
    public ModelAndView handleInternalError(@ModelAttribute("ex") GenreException ex){
        return new ModelAndView("/error/500","error",ex.getMessage());
    }

    @ExceptionHandler(AuthorException.class)
    public ModelAndView handleInternalError(@ModelAttribute("ex") AuthorException ex){
        return new ModelAndView("/error/500","error",ex.getMessage());
    }
}
