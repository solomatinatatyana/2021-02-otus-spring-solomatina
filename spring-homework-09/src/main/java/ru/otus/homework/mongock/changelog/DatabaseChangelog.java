package ru.otus.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.author.AuthorRepository;
import ru.otus.homework.repository.book.BookRepository;
import ru.otus.homework.repository.genre.GenreRepository;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    @ChangeSet(order = "000", id = "dropDB", author = "tsolomatina", runAlways = true)
    public void dropDB(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "insert", author = "tsolomatina", runAlways = true)
    public void insert(
            AuthorRepository authorRepository, GenreRepository genreRepository,
            BookRepository bookRepository) {
        Author author1 = authorRepository.save(new Author("Джон Толкин"));
        Author author2 = authorRepository.save(new Author("Лев Толстой"));
        Author author3 = authorRepository.save(new Author("Роберт Желязны"));

        Genre genre1 = genreRepository.save(new Genre("Фантастика"));
        Genre genre2 = genreRepository.save(new Genre("Роман"));

        Comment comment1Book1 = new Comment("Отличная книга. 10 из 10", 10);
        Comment comment1Book2 = new Comment("Нормальная. 5 из 10", 5);
        Comment comment2Book1 = new Comment("Хорошая книга. 8 из 10", 8);
        Comment comment2Book2 = new Comment("Выше среденего. 6 из 10", 6);
        Comment comment1Book3 = new Comment("Супер", 7);
        Comment comment1Book4 = new Comment("Понравилась", 0);

        bookRepository.save(new Book("Властелин колец", author1,genre1, comment1Book1, comment2Book1));
        bookRepository.save(new Book("Война и мир", author2,genre2, comment1Book2, comment2Book2));
        bookRepository.save(new Book("Хоббит или туда и обратно",author1,genre1, comment1Book3));
        bookRepository.save(new Book("Девять принцев Амбера", author3,genre1, comment1Book4));
    }
}
