package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
