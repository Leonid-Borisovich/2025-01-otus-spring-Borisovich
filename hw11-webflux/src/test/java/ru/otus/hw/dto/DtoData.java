package ru.otus.hw.dto;

import ru.otus.hw.rest.dto.AuthorDto;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.rest.dto.CommentDto;
import ru.otus.hw.rest.dto.GenreDto;

import java.util.List;
import java.util.stream.IntStream;

public class DtoData {
    public static List<AuthorDto> getDtoAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new AuthorDto("_a" + id, "Author_" + id))
                .toList();
    }

    public  static List<GenreDto> getDtoGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new GenreDto("_g" + id, "Genre_" + id))
                .toList();
    }

    public  static List<CommentDto> getDtoComments() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new CommentDto("_c" + id, "text_of_comment_" + id))
                .toList();
    }


    public static List<BookDto> getDtoBooks(List<AuthorDto> dbAuthors, List<GenreDto> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new BookDto("_b" + id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    public  static List<BookDto> getDtoBooks() {
        var dbAuthors = getDtoAuthors();
        var dbGenres = getDtoGenres();
        var dbComments = getDtoComments();
        var books = getDtoBooks(dbAuthors, dbGenres);
        books.get(0).setComments(dbComments);
        return books;
    }
}
