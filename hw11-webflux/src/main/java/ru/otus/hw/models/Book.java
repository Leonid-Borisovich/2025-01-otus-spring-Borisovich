package ru.otus.hw.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
@EqualsAndHashCode
@ToString
public class Book {
    @Id
    private String id;

    private String title;

    @EqualsAndHashCode.Exclude
    @DBRef
    private Author author;

    @EqualsAndHashCode.Exclude
    @DBRef
    private Genre genre;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments;

    public Book(String id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = new ArrayList<>();
    }

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = new ArrayList<>();
    }

}
