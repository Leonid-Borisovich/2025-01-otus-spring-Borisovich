package ru.otus.hw.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Book {
    private long id;

    private String title;

    @EqualsAndHashCode.Exclude
    private Author author;

    @EqualsAndHashCode.Exclude
    private Genre genre;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments;

    public Book(long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = new ArrayList<>();
    }
}
