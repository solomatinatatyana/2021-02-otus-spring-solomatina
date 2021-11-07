package ru.otus.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework.domain.Author;
import ru.otus.homework.repository.author.AuthorRepository;
import ru.otus.homework.repository.book.BookRepository;
import ru.otus.homework.repository.comment.CommentRepository;
import ru.otus.homework.repository.genre.GenreRepository;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "first", author = "tsolomatina", runAlways = true)
    public void firstSet(MongoDatabase db) {
        db.drop();
        /*authorRepository.save(new Author("Джон Толкин"));
        authorRepository.save(new Author("Лев Толстой"));
        authorRepository.save(new Author("Роберт Желязны"));*/
    }

    /*@ChangeSet(order = "002", id = "insertLermontov", author = "ydvorzhetskiy")
    public void insertLermontov(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("persons");
        var doc = new Document().append("name", "Lermontov");
        myCollection.insertOne(doc);
    }*/

    @ChangeSet(order = "002", id = "insert", author = "tsolomatina")
    public void insert(
            AuthorRepository authorRepository, GenreRepository genreRepository,
            CommentRepository commentRepository, BookRepository bookRepository) {
        authorRepository.save(new Author("Джон Толкин"));
        authorRepository.save(new Author("Лев Толстой"));
    }
}
