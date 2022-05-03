package ru.otus.homework.service.books;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.repository.author.AuthorRepository;
import ru.otus.homework.repository.book.BookRepository;
import ru.otus.homework.repository.genre.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.homework.util.SleepUtil.sleepRandomly;

@Service
public class BookServiceImpl implements BookService{
    private final MutableAclService mutableAclService;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(MutableAclService mutableAclService, BookRepository bookRepository, GenreRepository genreRepository,
                           AuthorRepository authorRepository) {
        this.mutableAclService = mutableAclService;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @HystrixCommand(commandKey="getBookKey", fallbackMethod="buildFallbackBookCreate")
    @Transactional
    @Override
    @PreAuthorize(value = "hasRole('ADMIN')")
    public void createBook(Book book, Author author, Genre genre) {
        sleepRandomly();
        book.setAuthor(author);
        book.setGenre(genre);
        if(!bookRepository.existsBookByTitle(book.getTitle())){
            bookRepository.saveAndFlush(book);
        }else {
            throw new BookException("book with title ["+ book.getTitle() +"] is already exist!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        ObjectIdentity oid = new ObjectIdentityImpl(book.getClass(), book.getId());

        final Sid admin = new GrantedAuthoritySid("ROLE_ADMIN");

        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(owner);
        acl.insertAce( acl.getEntries().size(), BasePermission.READ, admin, true );
        acl.insertAce( acl.getEntries().size(), BasePermission.WRITE, admin, true );

        mutableAclService.updateAcl(acl);
    }

    @HystrixCommand(commandKey="getBookKey", fallbackMethod="buildFallbackBookUpdate")
    @Override
    @PostAuthorize("hasPermission(#book, 'WRITE')")
    public void updateBookById(long id, Book book) {
        sleepRandomly();
        Book bookToBeUpdated = getBookById(id);
        bookToBeUpdated.setTitle(book.getTitle());
        bookToBeUpdated.setGenre(genreRepository.findByName(book.getGenre().getName()).get());
        bookToBeUpdated.setAuthor(authorRepository.findByFullName(book.getAuthor().getFullName()).get());
        bookRepository.saveAndFlush(bookToBeUpdated);
    }

    @HystrixCommand(commandKey="getBookKey", fallbackMethod="buildFallbackBooks")
    @Override
    public Book getBookByTitle(String title) {
        sleepRandomly();
        return bookRepository.findByTitle(title).orElseThrow(()-> new BookException("Book with id [" + title + "] not found"));
    }

    @HystrixCommand(commandKey="getBookKey", fallbackMethod="buildFallbackBookById")
    @Override
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Book getBookById(long id) {
        sleepRandomly();
        return bookRepository.findById(id).orElseThrow(()-> new BookException("Book with id [" + id + "] not found"));
    }

    /*@HystrixCommand(commandKey="getBookKey", fallbackMethod="buildFallbackAllBooks", commandProperties= {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000")
})*/
    @Override
    public List<Book> getAllBooks() {
       // sleepRandomly();
        return bookRepository.findAll();
    }

    @HystrixCommand(commandKey="getBookKey", fallbackMethod="buildFallbackBookDeleteById")
    @Transactional
    @Override
    @PreAuthorize(value = "hasRole('ADMIN')")
    public void deleteBookById(long id) {
        sleepRandomly();
        bookRepository.deleteBookByIdWithComments(id);
    }

    @HystrixCommand(commandKey="getBookKey", fallbackMethod="buildFallbackBookDeleteByTitle")
    @Transactional
    @Override
    @PreAuthorize(value = "hasRole('ADMIN')")
    public void deleteBookByTitle(String title) {
        sleepRandomly();
        bookRepository.deleteBookByTitleWithComments(title);
    }

    @Override
    public List<BookComments> getBookCommentsCount() {
        return bookRepository.findBooksCommentsCount();
    }

    @Override
    public List<Book> getAllBooksWithGivenGenre(String genre) {
        return genreRepository.findByName(genre).get().getBooks();
    }

    @Override
    public List<Book> getAllBooksWithGivenAuthor(String author) {
        return bookRepository.findAllByAuthor_FullName(author);
    }

    /**
     * Получить все комментарии по книге
     * @param bookId id книги
     * @return List<Comment>
     */
    @Override
    public List<Comment> getCommentsByBookId(long bookId){
        return bookRepository.findAll().stream()
                .filter(b -> b.getId() == (bookId))
                .map(Book::getComments)
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public long getBookCommentsCount(int bookId) {
        return getCommentsByBookId(bookId).size();
    }

    public Book buildFallbackBooks(String bookTitle) {
        Book book = new Book();
        Genre genre = new Genre(0L, "N/A genre");
        Author author = new Author(0L, "N/A author");
        book.setId(0L);
        book.setTitle(bookTitle);
        book.setGenre(genre);
        book.setAuthor(author);
        return book;
    }

    public Book buildFallbackBookById(long id) {
        Book book = new Book();
        Genre genre = new Genre(0L, "N/A genre");
        Author author = new Author(0L, "N/A author");
        book.setId(id);
        book.setTitle("N/A bookTitle");
        book.setGenre(genre);
        book.setAuthor(author);
        return book;
    }

    public List<Book> buildFallbackAllBooks() {
        Book book = new Book();
        Genre genre = new Genre(0L, "N/A genre");
        Author author = new Author(0L, "N/A author");
        book.setId(0L);
        book.setTitle("N/A bookTitle");
        book.setGenre(genre);
        book.setAuthor(author);
        List<Book> books = new ArrayList<>();
        books.add(book);
        return books;
    }

    public void buildFallbackBookUpdate(long id, Book book) {
        System.out.println(book.getTitle() + " not updated");
    }

    public void buildFallbackBookCreate(Book book, Author author, Genre genre) {
        System.out.println(book.getTitle() + " not created");
    }

    public void buildFallbackBookDeleteById(long id) {
        System.out.println("book with " + id + " not deleted");
    }

    public void buildFallbackBookDeleteByTitle(String title) {
        System.out.println("book with " + title + " not deleted");
    }
}
