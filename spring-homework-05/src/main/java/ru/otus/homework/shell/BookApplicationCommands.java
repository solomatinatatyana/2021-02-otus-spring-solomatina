package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Book;
import ru.otus.homework.service.books.BookService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookApplicationCommands {
    private final BookService bookService;

    private long id;
    private String bookName;
    private long authorId;
    private long genreId;

    @ShellMethod(value = "getting book by id", key = {"getBookById", "gbId"})
    public Book getBookById(@ShellOption long id){
        this.id = id;
        return bookService.getBookById(id);
    }

    @ShellMethod(value = "getting book by name", key = {"getBookByName", "gbn"})
    public Book getBookByName(@ShellOption String bookName){
        this.bookName = bookName;
        return bookService.getBookByTitle(bookName);
    }

    @ShellMethod(value = "get all books", key = {"getAllBooks", "gab"})
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @ShellMethod(value = "insert new book", key = {"insertBook", "ib"})
    public void insertBook(@ShellOption long id,
                           @ShellOption String title,
                           @ShellOption long authorId,
                           @ShellOption long genreId){
        this.id = id;
        this.bookName = title;
        this.authorId = authorId;
        this.genreId = genreId;
        bookService.insertBook(id, title, authorId, genreId);
    }

    @ShellMethod(value = "update book title", key = {"updateBook", "ubId"})
    public void updateBookTitleById(@ShellOption long id, @ShellOption String title){
        bookService.updateBookTitleById(id, title);
    }

    @ShellMethod(value = "delete book by id", key = {"deleteBookById", "dbId"})
    public void deleteBookById(@ShellOption long id){
        bookService.deleteBookById(id);
    }

    @ShellMethod(value = "delete book by title", key = {"deleteBookByTitle", "dbt"})
    public void deleteBookById(@ShellOption String title){
        bookService.deleteBookByTitle(title);
    }
}
