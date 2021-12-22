package ru.otus.homework.repository.genre;

import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Genre;

@Component
public class GenreRepositoryImpl implements GenreRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public GenreRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createGenre(Genre genre) {
        mongoTemplate.insert(genre);
        System.out.println("Genre ["+genre.getName()+"] created successfully");
    }

    @Override
    public void updateGenreById(String id, Genre genre) {
        Query query= new Query(Criteria.where("id").is(genre.getId()));
        Update update = new Update();
        update.set("name",genre.getName());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Genre.class);

        if(result == null){
            System.out.println("No genres updated");
        }else
            System.out.println(result.getModifiedCount() + " genre(s) updated");
    }
}
