package ru.otus.homework.repository.comment;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.util.RawResultPrinter;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final MongoTemplate mongoTemplate;
    private final RawResultPrinter rawResultPrinter;

    @Override
    public List<Comment> findBookCommentById(String bookId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("id").is(bookId))
                , unwind("comments")
                , project().andExclude("_id").and(valueOfToArray("comments")).as("comments_map")
                , project().and("comments_map").arrayElementAt(1).as("comments_id_map")
                , project().and("comments_id_map.v").as("comments_id")
                , lookup("comments", "comments_id", "_id", "comments")
                , unwind("comments")
                , project().and("comments._id").as("_id").and("comments.comment_text").as("comment_text")
        );

        Document rawResults = mongoTemplate.aggregate(aggregation, Book.class, Comment.class).getRawResults();
        rawResultPrinter.prettyPrintRawResult(rawResults);
        return mongoTemplate.aggregate(aggregation, Book.class, Comment.class).getMappedResults();
    }
}
