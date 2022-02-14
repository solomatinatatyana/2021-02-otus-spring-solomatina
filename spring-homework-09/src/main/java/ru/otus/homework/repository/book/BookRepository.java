package ru.otus.homework.repository.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,String>{

    List<Book> findAll();

    Optional<Book> findByTitle(String title);

    boolean existsBookByTitle(String title);


    /*{$unwind : "$comments"},
    {$project: {_id : "$_id", rating : "$comments.rating"}},
    {$group : {_id: "$_id", commentsCount: {$sum:1},
    avgCommentsRating : {$avg : "$rating"}}}
*/
}
