package ru.otus.homework.repository.comment;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryJpaImpl implements CommentRepositoryJpa{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if(comment.getId()==0){
            em.persist(comment);
            em.flush();
            return comment;
        }else {
            return em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(long id) {
        Comment comment = em.find(Comment.class, id);
        try {
            return Optional.of(comment);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c",Comment.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Comment comment = em.find(Comment.class, id);
        em.remove(comment);
        em.flush();
    }
}
