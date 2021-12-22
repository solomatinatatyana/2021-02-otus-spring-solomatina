package ru.otus.homework.repository.author;

import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Author;

@Component
public class AuthorRepositoryImpl implements AuthorRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    public AuthorRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createAuthor(Author author) {
        mongoTemplate.insert(author);
        System.out.println("Author ["+author.getFullName()+"] created successfully");
    }

    @Override
    public void updateAuthorById(String id, Author author) {
        Query query= new Query(Criteria.where("id").is(author.getId()));
        Update update = new Update();
        update.set("fullName",author.getFullName());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Author.class);

        if(result == null){
            System.out.println("No authors updated");
        }else
            System.out.println(result.getModifiedCount() + " author(s) updated");
    }
}
