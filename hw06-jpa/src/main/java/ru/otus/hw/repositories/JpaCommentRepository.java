package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {


    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllForBook(long bookId) {
        TypedQuery<Comment> query = em.createQuery(

                      "select c from Book b inner join  b.comments  c " +
                        " where b.id  = :book_id", Comment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        var book = em.find(Book.class, comment.getBookId());
        if (Objects.isNull(book))
            throw new EntityNotFoundException("Can`t  find book with id =" + comment.getBookId());

        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void deleteById(long id) {
        Comment com = em.find(Comment.class, id);
        if (com == null) {
            throw new EntityNotFoundException("Can`t  find comment with id =" + id);
        }
        em.remove(com);
    }
}
