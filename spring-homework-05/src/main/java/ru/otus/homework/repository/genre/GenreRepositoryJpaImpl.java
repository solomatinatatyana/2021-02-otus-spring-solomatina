package ru.otus.homework.repository.genre;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpaImpl implements GenreRepositoryJpa {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Genre save(Genre genre) {
       if(genre.getId()==0){
           em.persist(genre);
           em.flush();
           return genre;
       }else {
           return em.merge(genre);
       }
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class,id));
    }

    @Override
    public Optional<Genre> findByName(String genre){
        TypedQuery<Genre> query = em.createQuery(
                "select b from Genre b where b.name = :genre"
                , Genre.class);
        query.setParameter("genre", genre);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g",Genre.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Genre genre = em.find(Genre.class, id);
        em.remove(genre);
        em.flush();
    }
}
