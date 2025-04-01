package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.hw.dto.DtoData;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Services на основе JPA для работы с книгами ")
@DataJpaTest
@ComponentScan({"ru.otus.hw.converters", "ru.otus.hw.repositories"})
@Import(BookServiceImpl.class)
class JpaBookServiceTest {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    CommentRepository commentRepository;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Book> dbBooks;

    private List<ru.otus.hw.dto.Author> dtoAuthors;

    private List<ru.otus.hw.dto.Genre> dtoGenres;

    private List<ru.otus.hw.dto.Book> dtoBooks;


    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);

        DtoData.getDtoBooks();
        dtoAuthors = DtoData.getDtoAuthors();
        dtoGenres = DtoData.getDtoGenres();
        dtoBooks = DtoData.getDtoBooks(dtoAuthors, dtoGenres);



    }

    @DisplayName("должен загружать книгу по id")
    @ParameterizedTest
    @MethodSource("getDtoBooks")
    void shouldReturnCorrectBookById(ru.otus.hw.dto.Book expectedBook) {
        IntStream.range(1, 4).boxed().forEach(id -> {
            when(bookRepository.findById(id)).thenReturn(Optional.of(dbBooks.get(id-1)));
        } );

        var actualBook = bookService.findById(expectedBook.getId());
        assertThat(actualBook).isPresent().get().isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        when(bookRepository.findAll()).thenReturn(dbBooks);
        var actualBooks = bookService.findAll();
        List<ru.otus.hw.dto.Book> expectedBooks = dtoBooks;

        assertThat(actualBooks).containsExactlyElementsOf(
                expectedBooks.stream()
                        .collect(Collectors.toList()));
        actualBooks.forEach(System.out::println);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var author = dbAuthors.get(0);
        var genre = dbGenres.get(0);
        var newBook = new Book(0, "BookTitle_10500", author, genre);
        var mockBook = new Book(4, "BookTitle_10500", author, genre);

        when(bookRepository.save(any())).thenReturn(mockBook);
        when(authorRepository.findById(1l)).thenReturn(Optional.of(author));
        when(genreRepository.findById(1l)).thenReturn(Optional.of(genre));

        var returnedBook = bookService.insert("BookTitle_10500", dbAuthors.get(0).getId(), dbGenres.get(0).getId());
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(mockBook);

    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Comment> getDbComments() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Comment(id, "text_of_comment_" + id, 1L))
                .toList();
    }


    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    private static List<ru.otus.hw.dto.Book> getDtoBooks(){
        return DtoData.getDtoBooks();
    }
}