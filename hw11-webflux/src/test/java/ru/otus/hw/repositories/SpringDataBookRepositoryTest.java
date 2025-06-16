package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий mongo на основе Spring Data для работы с книгами ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.hw.converters", "ru.otus.hw.repositories"})
class SpringDataBookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookReactRepository bookReactRepository;
    @Autowired
    private AuthorReactRepository authorReactRepository;
    @Autowired
    private GenreReactRepository genreReactRepository;

    private List<Author> dbAuthors;
    private List<Genre> dbGenres;
    private List<Book> dbBooks;


    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        var dbComments = getDbComments();

        dbBooks = getDbBooks(dbAuthors, dbGenres);
        dbBooks.get(0).setComments(dbComments);

        bookRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();
        initBooks();
    }

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        Book expectedBook = dbBooks.get(0);
        Mono<Book> actualBook = bookReactRepository.findById(expectedBook.getId());

        StepVerifier
                .create(actualBook)
                .assertNext(book -> assertThat(book).usingRecursiveComparison().isEqualTo(expectedBook))
                .expectComplete()
                .verify();
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        Flux<Book> actualBooks = bookReactRepository.findAll();
        Book expectedBook = dbBooks.get(0);

        StepVerifier
                .create(actualBooks)
                .assertNext(book -> assertThat(book).usingRecursiveComparison().isEqualTo(expectedBook))
                .expectNextCount(2)
                .expectComplete()
                .verify();

    }

     @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var newBook = new Book( "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0));
        var returnedBook = bookReactRepository.save(newBook);
        assertThat(returnedBook).isNotNull();

         StepVerifier
                 .create(returnedBook)
                 .assertNext(book -> assertThat(book).usingRecursiveComparison().isEqualTo(newBook))
                 .expectComplete()
                 .verify();
     }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book("_b1", "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2));

        assertThat(bookRepository.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var returnedBook = bookReactRepository.save(expectedBook);
        StepVerifier
                .create(returnedBook)
                .assertNext(book -> assertThat(book).usingRecursiveComparison().isEqualTo(expectedBook))
                .expectComplete()
                .verify();

    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertNotNull(bookReactRepository.findById("_b1"));

        Mono<Book> mono = bookReactRepository.deleteById("_b1")
        .then(bookReactRepository.findById("_b1"));
        StepVerifier
                .create(mono)
                .expectComplete()
                .verify();
    }

    @DisplayName("не должен вызывать исключение при попытке удалить несуществующую книгу по id ")
    @Test
    void shouldThrowExceptionByDeleting() {
        assertDoesNotThrow(()-> {
            bookReactRepository.deleteById("id_that_not_exist");
        });
    }

     private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author("_a" + id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre("_g" + id, "Genre_" + id))
                .toList();
    }

    private static List<Comment> getDbComments() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Comment("_c" + id, "text_of_comment_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book("_b" + id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        var dbComments = getDbComments();
        var books = getDbBooks(dbAuthors, dbGenres);
        books.get(0).setComments(dbComments);
        return books;
    }

    private void initBooks() {
        var a1 = authorRepository.save(new Author("_a1", "Author_1"));
        var a2 = authorRepository.save(new Author("_a2", "Author_2"));
        var a3 = authorRepository.save(new Author("_a3", "Author_3"));

        var g1 = genreRepository.save(new Genre("_g1", "Genre_1"));
        var g2 = genreRepository.save(new Genre("_g2", "Genre_2"));
        var g3 = genreRepository.save(new Genre("_g3", "Genre_3"));


        var b1 = bookRepository.save(new Book("_b1", "BookTitle_1",  a1, g1));
        b1.setComments(IntStream.range(1, 4).boxed()
                .map(id -> new Comment("_c" + id, "text_of_comment_" + id))
                .toList());

        bookRepository.save(b1);
        bookRepository.save(new Book("_b2", "BookTitle_2",  a2, g2));
        bookRepository.save(new Book("_b3", "BookTitle_3",  a3, g3));
    }


}