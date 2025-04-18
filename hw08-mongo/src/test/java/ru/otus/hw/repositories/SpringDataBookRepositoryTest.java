package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.BookServiceImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий mongo на основе Spring Data для работы с книгами ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.hw.converters", "ru.otus.hw.repositories"})
@Import(BookServiceImpl.class)
class SpringDataBookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookService bookService;

    private List<Author> dbAuthors;
    private List<Genre> dbGenres;
    private List<Book> dbBooks;


    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);

        bookRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();
        initBooks();
    }

    @DisplayName("должен загружать книгу по id")
    @ParameterizedTest
    @MethodSource("getDbBooks")
    void shouldReturnCorrectBookById(Book expectedBook) {
        var actualBook = bookRepository.findById(expectedBook.getId());

        assertThat(actualBook).isPresent()
                .get()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = bookRepository.findAll();
        var expectedBooks = dbBooks;

        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
        actualBooks.forEach(System.out::println);
    }

    @DisplayName("должен загружать список комментариев для книги")
    @ParameterizedTest
    @MethodSource("getDbBooks")
    void shouldReturnCorrectCommentList(Book expectedBook) {
        Book book = bookRepository.findById(expectedBook.getId()).get();
        var actualComments = book.getComments();
        var expectedComments = expectedBook.getComments();

        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
        actualComments.forEach(System.out::println);
    }


    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book( "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0));
        var returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> Objects.nonNull(book.getId()))
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookRepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book("_b1", "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2));

        assertThat(bookRepository.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> Objects.nonNull(book.getId()))
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookRepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertNotNull(bookRepository.findById("_b1"));
        bookRepository.deleteById("_b1");
        assertThat(bookRepository.findById("_b1")).isNotPresent();
    }

    @DisplayName("не должен вызывать исключение при попытке удалить несуществующую книгу по id ")
    @Test
    void shouldThrowExceptionByDeleting() {
        assertDoesNotThrow(()-> {
            bookRepository.deleteById("id_that_not_exist");
        });
    }

    @DisplayName("при попытке сохранить некорректные связи должен их обнулять")
    @Test
    void shouldThrowException() {
        Book book = bookRepository.findById("_b1").get();
        assertNotNull(book);

        var author = new Author("_a100", "xxx");
        book.setAuthor(author);
        bookRepository.save(book);

        book = bookRepository.findById("_b1").get();
        assertNull(book.getAuthor());
    }


    @DisplayName("должен сохранять комментарии в рамках сохранении книги")
    @Test
    void shouldSaveNewComment() {
        assertThat(bookRepository.findById("_b2")).isPresent();
        Book book = bookRepository.findById("_b2").get();
        assertThat(book.getComments().size() == 0);
        book.getComments().add(new Comment("very good_10500"));
        bookRepository.save(book);

        book = bookRepository.findById("_b2").get();
        assertThat(book.getComments().stream().filter(c -> "very good_10500".equals(c.getText())).count() == 1);

    }

    @DisplayName("должен уметь удалять комментарии")
    @Test
    void shouldDelComment() {
        assertThat(bookRepository.findById("_b1")).isPresent();
        Book book = bookRepository.findById("_b1").get();
        assertThat(book.getComments().size() > 0);
        Book updatedBook = bookService.delComment("_b1", "_c1");
        assertThat(updatedBook.getComments().stream().filter(c -> "text_of_comment_1".equals(c.getText())).count() == 0);

        book = bookRepository.findById("_b1").get();
        assertThat(book.getComments().stream().filter(c -> "text_of_comment_1".equals(c.getText())).count() == 0);

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