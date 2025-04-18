package ru.otus.hw.services;

import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<ru.otus.hw.dto.Book> findById(String id);

    List<ru.otus.hw.dto.Book> findAll();

    Book insert(String title, String authorId, String genreId);

    Book update(String id, String title, String authorId, String genreId);

    Book addComment(String id, String text);

    Book delComment(String bookId, String commentId);

    void deleteById(String id);
}
