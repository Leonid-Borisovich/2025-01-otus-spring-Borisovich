package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends ListCrudRepository<Book, Long> {
    Optional<Book> findById(long id);

    @Query("select b from Book b left join fetch b.genre " +
            "left join fetch b.comments left join fetch b.author")
    List<Book> findAll();

    Book save(Book book);

    void deleteById(long id);
}
