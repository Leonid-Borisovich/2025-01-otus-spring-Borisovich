package ru.otus.hw.rest.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BookDto {
    private String id;

    private String title;

    @EqualsAndHashCode.Exclude
    private AuthorDto author;

    @EqualsAndHashCode.Exclude
    private GenreDto genre;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<CommentDto> comments;

    public BookDto(String id, String title, AuthorDto author, GenreDto genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = new ArrayList<>();
    }

}
