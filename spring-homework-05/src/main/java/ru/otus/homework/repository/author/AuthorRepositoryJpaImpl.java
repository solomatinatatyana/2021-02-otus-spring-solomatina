package ru.otus.homework.repository.author;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author insert(Author author) {
        if(author.getId()==0){
            em.persist(author);
            em.flush();
            return author;
        }else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author = em.find(Author.class, id);
        try {
            return Optional.of(author);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Author author = em.find(Author.class, id);
        em.remove(author);
        em.flush();
    }
}
