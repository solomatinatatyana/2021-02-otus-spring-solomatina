package ru.otus.homework.repository.book;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookComments;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book book) {
        if(book.getId()==0){
            em.persist(book);
            em.flush();
            return book;
        }else {
            return em.merge(book);
        }
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
        Book book = em.find(Book.class, id);
        try {
            return Optional.of(book);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll(){
        EntityGraph<?> entityGraph = em.getEntityGraph("authors-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre", Book.class);
        query.setHint("javax.persistence.fetchgraph",entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
        em.flush();
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
}
