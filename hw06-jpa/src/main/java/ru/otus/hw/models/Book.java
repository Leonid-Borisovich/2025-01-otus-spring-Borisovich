package ru.otus.hw.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
@EqualsAndHashCode
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @EqualsAndHashCode.Exclude
    @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY) // TODO , cascade = CascadeType.ALL
    @JoinColumn(name = "author_id")
    private Author author;

    @EqualsAndHashCode.Exclude
    @ManyToOne(targetEntity = Genre.class, fetch = FetchType.LAZY) // TODO ALL ?
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(targetEntity = Comment.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE},
            orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;

    public Book(long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = new ArrayList<>();
    }
}
