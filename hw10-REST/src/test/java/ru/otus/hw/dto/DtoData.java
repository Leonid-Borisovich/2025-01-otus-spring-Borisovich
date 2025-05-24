package ru.otus.hw.dto;

import java.util.List;
import java.util.stream.IntStream;

public class DtoData {
    public static List<AurhorDto> getDtoAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new AurhorDto(id, "Author_" + id))
                .toList();
    }

    public  static List<GenreDto> getDtoGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new GenreDto(id, "Genre_" + id))
                .toList();
    }

    public  static List<CommentDto> getDtoComments() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new CommentDto(id, "text_of_comment_" + id, 1L))
                .toList();
    }


    public static List<BookDto> getDtoBooks(List<AurhorDto> dbAuthors, List<GenreDto> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new BookDto(id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    public  static List<BookDto> getDtoBooks() {
        var dbAuthors = getDtoAuthors();
        var dbGenres = getDtoGenres();
        var dbComments = getDtoComments();
        var books = getDtoBooks(dbAuthors, dbGenres);
        books.get(0).setCommentDtos(dbComments);
        return books;
    }
}
