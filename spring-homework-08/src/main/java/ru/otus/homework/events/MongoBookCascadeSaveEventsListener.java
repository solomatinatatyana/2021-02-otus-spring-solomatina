package ru.otus.homework.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Book;
import ru.otus.homework.repository.author.AuthorRepository;
import ru.otus.homework.repository.genre.GenreRepository;

//@Component
@RequiredArgsConstructor
public class MongoBookCascadeSaveEventsListener extends AbstractMongoEventListener<Object> {

    //private final GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        super.onBeforeConvert(event);
        /*Book book = event.getSource();
        if(book.getAuthor()!=null){

        }
        if (book.getGenre() != null) {
            genreRepository.save(book.getGenre());
        }*/
        Object source = event.getSource();
        if ((source instanceof Book) && (((Book) source).getAuthor() != null)) {
            authorRepository.save(((Book) source).getAuthor());
        }
    }
}
