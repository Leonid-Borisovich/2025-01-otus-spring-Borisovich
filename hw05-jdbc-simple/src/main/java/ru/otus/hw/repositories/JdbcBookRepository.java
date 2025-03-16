package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> findById(long id) {
        List<Book> books = jdbc.query(
                "select b.id as book_id, b.title, a.id as author_id, a.full_name, g.id as genre_id, g.name" +
                        " from books b" +
                        " inner join authors a on b.author_id=a.id" +
                        " inner join genres g on b.genre_id = g.id" +
                        " where b.id = :id",
                Collections.singletonMap("id", id),
                new BookRowMapper());

        return Optional.ofNullable(books.stream().findFirst()).orElse(Optional.empty());
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("select b.id as book_id, b.title, a.id as author_id, a.full_name, g.id as genre_id, g.name" +
                        " from books b" +
                        " inner join authors a on (b.author_id=a.id)" +
                        " inner join genres g on (b.genre_id = g.id)",
                new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        int res = jdbc.update("delete from books where id = :id", params);
        if (res == 0) {
            throw new EntityNotFoundException("Can`t  delete id =" + id);
        }
    }




    private Book insert(Book book) {
        Long authorId = Optional.ofNullable(book.getAuthor())
                .map(a -> a.getId()).orElse(null);
        Long genreId = Optional.ofNullable(book.getGenre())
                .map(a -> a.getId()).orElse(null);

        var keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author_id", authorId)
                .addValue("genre_id", genreId);

        int res = jdbc.update("insert into books (title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                params,
                keyHolder,
                new String[] {"id"});
        if (res == 0) {
            throw new EntityNotFoundException("Can`t  insert book!");
        }
        book.setId(keyHolder.getKey().longValue());
        return book;
    }

    private Book update(Book book) {
        Long authorId = Optional.ofNullable(book.getAuthor())
                .map(a -> a.getId()).orElse(null);
        Long genreId = Optional.ofNullable(book.getGenre())
                .map(a -> a.getId()).orElse(null);

        int res = jdbc.update("update books set title= :title, author_id= :author_id, genre_id= :genre_id" +
                        " where id = :id",
                Map.of("id", book.getId(),
                        "title", book.getTitle(),
                        "author_id", authorId,
                        "genre_id", genreId
                ));
        if (res == 0) {
            throw new EntityNotFoundException("Can`t  insert book!");
        }
        if (res > 1) {
            throw new IllegalStateException("Not unique id = " + book.getId());
        }
        return book;

    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long authorId = rs.getLong("author_id");
            String fullName = rs.getString("full_name");
            Author author = Objects.isNull(authorId) ? null : new Author(authorId, fullName);

            Long genreId = rs.getLong("genre_id");
            String name = rs.getString("name");
            Genre genre = Objects.isNull(genreId) ? null : new Genre(authorId, name);

            Long id = rs.getLong("book_id");
            if (id == null) {
                return null;
            }

            String title = rs.getString("title");
            return new Book(id, title, author, genre);
        }
    }
}
