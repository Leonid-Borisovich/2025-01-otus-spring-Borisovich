package ru.otus.hw.dto;

import java.util.List;
import java.util.stream.IntStream;

public class DtoData {
    public static List<Author> getDtoAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    public  static List<Genre> getDtoGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    public  static List<Comment> getDtoComments() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Comment(id, "text_of_comment_" + id, 1L))
                .toList();
    }


    public static List<Book> getDtoBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    public  static List<Book> getDtoBooks() {
        var dbAuthors = getDtoAuthors();
        var dbGenres = getDtoGenres();
        var dbComments = getDtoComments();
        var books = getDtoBooks(dbAuthors, dbGenres);
        books.get(0).setComments(dbComments);
        return books;
    }
}
