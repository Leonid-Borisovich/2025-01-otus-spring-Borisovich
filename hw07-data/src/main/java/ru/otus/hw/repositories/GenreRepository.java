package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends ListCrudRepository<Genre, Long> {
    List<Genre> findAll();

    Optional<Genre> findById(long id);
}
