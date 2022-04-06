package ru.otus.homework.rest;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
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

import static ru.otus.homework.metrics.Metrics.Books.*;

@Controller
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

    @Timed(GET_BOOKS_REQ_TIME)
    @GetMapping(value = "/book")
    public String getBooks(@RequestParam(required = false, name = "author") String author,
                           @RequestParam(required = false, name = "genre") String genre,
                           Model model){
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

    @Timed(GET_BOOK_EDIT_REQ_TIME)
    @GetMapping(value = "/book/{id}/edit")
    public String editBookForm(@PathVariable("id") long id, Model model){
        BookDto book = bookMapper.toBookDto(bookService.getBookById(id));
        List<GenreDto> genreList = genreMapper.toGenreDtoList(genreService.getAllGenres());
        List<AuthorDto> authorList = authorMapper.toAuthorDtoList(authorService.getAllAuthors());
        model.addAttribute("book",book);
        model.addAttribute("genres",genreList);
        model.addAttribute("authors",authorList);
        return "book-edit";
    }

    @Timed(PATCH_BOOK_REQ_TIME)
    @PatchMapping(value = "/book/{id}")
    public String saveBook(@PathVariable("id") long id,
                           @ModelAttribute("book") BookDto book){
        bookService.updateBookById(id, bookMapper.toBook(book));
        return "redirect:/book";
    }

    @Timed(GET_BOOK_ADD_REQ_TIME)
    @GetMapping(value = "/book/add")
    public String showAddBookForm(Model model){
        model.addAttribute("book",new Book());
        return "book-add";
    }

    @Timed(CREATE_BOOK_REQ_TIME)
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

    @Timed(DELETE_BOOK_REQ_TIME)
    @DeleteMapping(value = "/book/{id}")
    public String deleteBook(@PathVariable("id") long id){
        bookService.deleteBookById(id);
        return "redirect:/book";
    }
}
