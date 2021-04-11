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
    public Comment insert(Comment comment) {
        if(comment.getId()==0){
            em.persist(comment);
            em.flush();
            return comment;
        }else {
            return em.merge(comment);
        }
    }

    @Override
    public void updateCommentById(long id, String commentText) {
        Query query = em.createQuery("update Comment c set c.commentText = :commentText where c.id = :id");
        query.setParameter("commentText",commentText);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Comment> findById(long id) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.id = :id", Comment.class);
        query.setParameter("id",id);
        try {
            return Optional.of(query.getSingleResult());
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
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
