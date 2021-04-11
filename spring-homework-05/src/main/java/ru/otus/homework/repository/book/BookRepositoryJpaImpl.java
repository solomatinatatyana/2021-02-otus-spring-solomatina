package ru.otus.homework.repository.book;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.dto.BookComments;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Book insert(Book book) {
        if(book.getId()==0){
            em.persist(book);
            em.flush();
            return book;
        }else {
            return em.merge(book);
        }
    }

    @Override
    public void updateTitleById(long id, String title) {
        Query query = em.createQuery("update Book b set b.title = :title where b.id = :id");
        query.setParameter("title",title);
        query.setParameter("id",id);
        query.executeUpdate();
    }

    @Override
    public Optional<Book> findByName(String title){
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.title = :title"
                , Book.class);
        query.setParameter("title", title);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.id = :id"
                , Book.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll(){
        EntityGraph<?> entityGraph = em.getEntityGraph("authors-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre join fetch b.comments", Book.class);
        query.setHint("javax.persistence.fetchgraph",entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id",id);
        query.executeUpdate();
    }

    @Override
    public void deleteByName(String title){
        Query query = em.createQuery("delete from Book b where b.title = :title");
        query.setParameter("title",title);
        query.executeUpdate();
    }

    @Override
    public List<BookComments> findBooksCommentsCount() {
        return em.createQuery(
                "select new ru.otus.homework.dto.BookComments(b, count(c)) " +
                        "from Book b left join b.comments c " +
                        "group by b " +
                        "order by count(c) desc "
                , BookComments.class).getResultList();
    }

    @Override
    public List<Book> findAllBooksWithGivenGenre(String genre) {
        EntityGraph<?> entityGraph = em.getEntityGraph("authors-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                "join fetch b.genre g " +
                "join fetch b.comments с where g.name = :genre ", Book.class);
        query.setHint("javax.persistence.fetchgraph",entityGraph);
        query.setParameter("genre", genre);
        return query.getResultList();
    }
}
