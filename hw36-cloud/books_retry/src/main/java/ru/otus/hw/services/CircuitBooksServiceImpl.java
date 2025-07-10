package ru.otus.hw.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class CircuitBooksServiceImpl implements  CircuitBooksService{

    private final BookConverter bookConverter;

    private List<Author> tmpAuthors;
    private List<Genre> tmpGenres;
    private List<Book> tmpBooks;

    public CircuitBooksServiceImpl(BookConverter bookConverter) {
        this.bookConverter = bookConverter;
    }

    public List<BookDto> getTmpBooks(){
        return tmpBooks.stream().map(bookConverter::modelToDto).toList();

    }

    @PostConstruct
    void setUp() {
        tmpAuthors = getRandomAuthors();
        tmpGenres = getRandomGenres();
        tmpBooks = getRandomBooks(tmpAuthors, tmpGenres);
    }


    private static List<Author> getRandomAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }


    private static List<Genre> getRandomGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> getRandomBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

}
