package ru.otus.hw.repositories;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookServiceImpl;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе JPA для работы с книгами ")
@DataJpaTest
@ComponentScan({"ru.otus.hw.converters", "ru.otus.hw.repositories"})
@Import(BookServiceImpl.class)
class JpaBookRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaBookRepository bookRepository;

    @Autowired CommentRepository commentRepository;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
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

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book(0, "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0));
        var returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookRepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book(1L, "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2));

        assertThat(bookRepository.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookRepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertNotNull(em.find(Book.class, 1L));
        bookRepository.deleteById(1L);
        assertNull(em.find(Book.class, 1L));
        em.flush();
        assertNull(em.find(Book.class, 1L));

    }

    @DisplayName("должен вызывать исключение при попытке удалить несуществующую книгу по id ")
    @Test
    void shouldThrowExceptionByDeleting() {
        assertNull(em.find(Book.class, 0L));
        assertThrows(EntityNotFoundException.class, () -> {
            bookRepository.deleteById(0L);
        });
    }

    @DisplayName("должен вызывать исключение  ViolationException при попытке сохранить некорректные связи")
    @Test
    void shouldThrowViolationException() {
        Book book = em.find(Book.class, 1L);
        assertNotNull(book);

        var author = new Author(10L, "xxx");
        book.setAuthor(author);

        assertThrows(ConstraintViolationException.class, () -> {
            bookRepository.save(book);
            em.flush();
        });
    }


    @DisplayName("должен загружать список комментариев для книги")
    @ParameterizedTest
    @MethodSource("getDbBooks")
    void shouldReturnCorrectCommentList(Book expectedBook) {
        var actualComments = commentRepository.findAllForBook(expectedBook.getId());
        var expectedComments = expectedBook.getComments();

        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
        actualComments.forEach(System.out::println);
    }

    @DisplayName("должен загружать комментарий")
    @Test
    void shouldReturnCorrectComment() {
        var comment = commentRepository.findById(1L);
        assertNotNull("text_of_comment_1".equals(comment.get().getText()));
    }


    @DisplayName("должен сохранять комментарий")
    @Test
    void shouldSaveNewComment() {
        var newComment = new Comment(0, "very good_10500", 1L);
        var returnedComment = commentRepository.save(newComment);

        em.flush();

        assertThat(bookRepository.findById(1L)).isPresent();
        assertThat(bookRepository.findById(1L).get().getComments().contains(returnedComment));

    }

    @DisplayName("должен удалять с книгой комментарии ")
    @Test
    void shouldDeleteCommentsWithBook() {
        assertNotNull(em.find(Book.class, 1L));

        var book = em.find(Book.class, 1L);
        assertThat(book.getComments().size() > 0);

        long commentId = book.getComments().get(0).getId();

        bookRepository.deleteById(1L);
        em.flush();

        assertNull(em.find(Comment.class, commentId));
    }

    @DisplayName("должен удалять комментарий")
    @Test
    void shouldDeleteComment() {
        assertNotNull(em.find(Book.class, 1L));
        var book = em.find(Book.class, 1L);
        assertThat(book.getComments().size() > 0);

        var comment1  = book.getComments().get(0);
        long commentId = comment1.getId();
        em.detach(book);

        commentRepository.deleteById(commentId);
        em.flush();

        assertNull(em.find(Comment.class, commentId));
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

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        var dbComments = getDbComments();
        var books = getDbBooks(dbAuthors, dbGenres);
        books.get(0).setComments(dbComments);
        return books;
    }
}