package ru.otus.homework.repository.book;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.util.RawResultPrinter;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

public class BookRepositoryImpl implements BookRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;
    private final RawResultPrinter rawResultPrinter;


    public BookRepositoryImpl(MongoTemplate mongoTemplate, RawResultPrinter rawResultPrinter) {
        this.mongoTemplate = mongoTemplate;
        this.rawResultPrinter = rawResultPrinter;
    }

    @Override
    public List<Book> findAllByAuthor_FullName(String fio) {
        Query authorQuery = new Query().addCriteria(Criteria.where("fullName").is(fio));
        Author foundAuthor = Optional.ofNullable(mongoTemplate.findOne(authorQuery, Author.class)).orElseThrow(()->new AuthorException("Author with fio [" + fio + "] not found"));
        Query queryBook = new Query().addCriteria(Criteria.where("author.$id").is(new ObjectId(foundAuthor.getId())));
        return mongoTemplate.find(queryBook,Book.class);
    }

    @Override
    public List<Book> findAllByGenre_Name(String genre) {
        Query genreQuery = new Query().addCriteria(Criteria.where("name").is(genre));
        Genre foundGenre = Optional.ofNullable(mongoTemplate.findOne(genreQuery, Genre.class)).orElseThrow(()->new GenreException("Genre with name [" + genre + "] not found"));
        Query queryBook = new Query().addCriteria(Criteria.where("genre.$id").is(new ObjectId(foundGenre.getId())));
        return mongoTemplate.find(queryBook,Book.class);
    }

    @Override
    public List<BookComments> getBookCommentsByBookId(String bookId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("id").is(bookId))
                , unwind("comments")
                , project().and("comments._id").as("_id").and("comments.rating").as("rating")
                ,group().count().as("commentsCount").avg("rating").as("avgCommentsRating")
        );
        Document rawResults = mongoTemplate.aggregate(aggregation, Book.class, BookComments.class).getRawResults();
        rawResultPrinter.prettyPrintRawResult(rawResults);
        return mongoTemplate.aggregate(aggregation, Book.class, BookComments.class).getMappedResults();
    }
}
