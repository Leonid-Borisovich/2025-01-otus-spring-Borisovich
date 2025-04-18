package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.Book;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    private final CommentConverter commentConverter;

    public ru.otus.hw.dto.Book modelToDto(ru.otus.hw.models.Book book) {

        return new ru.otus.hw.dto.Book(book.getId(), book.getTitle(),
                authorConverter.modelToDto(book.getAuthor()),
                genreConverter.modelToDto(book.getGenre()),
                commentConverter.modelToDto(book.getComments()));
    }


    public String dtoToString(Book book) {
        return "Id: %s, title: %s, author: {%s}, genres: [%s], comments: {%s}".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.dtoToString(book.getAuthor()),
                genreConverter.dtoToString(book.getGenre()),
                commentConverter.dtoToString(book.getComments()));
    }
}
