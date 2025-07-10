package ru.otus.hw.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private  final BookConverter bookConverter;

    private final CircuitBooksService circuitBooksService;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id).map(bookConverter::modelToDto);
    }

    @Override
    @Transactional(readOnly = true)
    //@Retry(name = "demoBreaker", fallbackMethod = "failsListPage")
    @CircuitBreaker(name = "demoBreaker", fallbackMethod = "failsListPage")
    public List<BookDto> findAll() {
        // Для демонстрации @Retry - кидаем исключение
        throw new RuntimeException("Circuit Break!");
        //return bookRepository.findAll().stream().map(bookConverter::modelToDto).collect(Collectors.toList());
    }

    public List<BookDto> failsListPage(Exception ex) {
        return circuitBooksService.getTmpBooks();
    }


    @Override
    @Transactional
    public BookDto insert(String title, long authorId, long genreId) {
        Book book = save(0, title, authorId, genreId);
        return bookConverter.modelToDto(book);
    }

    @Override
    @Transactional
    public BookDto update(long id, String title, long authorId, long genreId) {
        Book book = save(id, title, authorId, genreId);
        return bookConverter.modelToDto(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Book save(long id, String title, long authorId, long genreId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
        var book = new Book(id, title, author, genre);
        return bookRepository.save(book);
    }
}
