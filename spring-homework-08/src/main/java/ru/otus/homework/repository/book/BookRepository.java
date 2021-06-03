package ru.otus.homework.repository.book;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookComments;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,String>, BookRepositoryCustom {

    List<Book> findAll();

    Optional<Book> findByTitle(String title);

    boolean existsBookByTitle(String title);


    /*{$unwind : "$comments"},
    {$project: {_id : "$_id", rating : "$comments.rating"}},
    {$group : {_id: "$_id", commentsCount: {$sum:1},
    avgCommentsRating : {$avg : "$rating"}}}
*/


    @Aggregation(
            "{$match : {_id: ?0}}," +
            "   {$unwind : $comments}," +
            "   {$project: {_id : $comments._id, rating : $comments.rating}}," +
            "   {$group : " +
                    "{_id: null, " +
                        "commentsCount: {$sum:1}, " +
                        "avgCommentsRating : {$avg : $rating}}}")
    List<BookComments> groupBy(String bookId);
}
