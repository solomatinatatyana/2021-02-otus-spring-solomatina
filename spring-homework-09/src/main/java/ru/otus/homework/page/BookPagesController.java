package ru.otus.homework.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.homework.domain.Book;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.rest.dto.GenreDto;
import ru.otus.homework.rest.mappers.AuthorMapper;
import ru.otus.homework.rest.mappers.BookMapper;
import ru.otus.homework.rest.mappers.CommentMapper;
import ru.otus.homework.rest.mappers.GenreMapper;
import ru.otus.homework.service.authors.AuthorService;
import ru.otus.homework.service.books.BookService;
import ru.otus.homework.service.genres.GenreService;

import java.util.List;

@Controller
public class BookPagesController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper bookMapper;
    private final GenreMapper genreMapper;
    private final AuthorMapper authorMapper;
    private final CommentMapper commentMapper;


    public BookPagesController(BookService bookService, AuthorService authorService,
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

    @GetMapping(value = "/book")
    public String getBooks(Model model){
        model.addAttribute("books", "books");
        return "book-list";
    }

    @GetMapping(value = "/book/add")
    public String showAddBookForm(Model model){
        model.addAttribute("book",new Book());
        return "book-add";
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
}
