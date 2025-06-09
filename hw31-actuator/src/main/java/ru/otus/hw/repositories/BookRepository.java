package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "thebook")
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b left join fetch b.genre " +
            "left join fetch b.comments left join fetch b.author")
    List<Book> findAll();

    @Query("select b from Book b " +
            "left join fetch b.genre left join fetch b.comments left join fetch b.author where b.id= :id")
    Optional<Book>findById(@Param("id") Long id);
}
