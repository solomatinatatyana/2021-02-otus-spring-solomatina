package ru.otus.homework.repository.book;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookComments;
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.repository.comment.CommentRepository;
import ru.otus.homework.util.RawResultPrinter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

public class BookRepositoryImpl implements BookRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final RawResultPrinter rawResultPrinter;


    public BookRepositoryImpl(MongoTemplate mongoTemplate, CommentRepository commentRepository, RawResultPrinter rawResultPrinter) {
        this.mongoTemplate = mongoTemplate;
        this.commentRepository = commentRepository;
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
                , project().andExclude("_id").and(valueOfToArray("comments")).as("comments_map")
                , project().and("comments_map").arrayElementAt(1).as("comments_id_map")
                , project().and("comments_id_map.v").as("comments_id")
                , lookup("comments", "comments_id", "_id", "comments")
                , unwind("comments")
                , project().and("comments._id").as("_id").and("comments.rating").as("rating")
                ,group().count().as("commentsCount").avg("rating").as("avgCommentsRating")
        );
        Document rawResults = mongoTemplate.aggregate(aggregation, Book.class, BookComments.class).getRawResults();
        rawResultPrinter.prettyPrintRawResult(rawResults);
        return mongoTemplate.aggregate(aggregation, Book.class, BookComments.class).getMappedResults();
    }

    @Override
    public void removeCommentsArrayElementsById(String id) {
        Query query = Query.query(Criteria.where("_id").is(new ObjectId(id)));
        Update update = new Update().pull("comments", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public void deleteBookByIdWithComments(String id) {
        List<Comment> comments = commentRepository.findBookCommentById(id);
        Iterable<String> commentIds = comments.stream().map(Comment::getId).collect(Collectors.toList());
        commentRepository.deleteAllById(commentIds);
        bookRepository.deleteById(id);
    }
}
