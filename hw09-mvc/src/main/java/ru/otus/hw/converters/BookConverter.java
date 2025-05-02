package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    private final CommentConverter commentConverter;

    public BookDto modelToDto(Book book) {

        return new BookDto(book.getId(), book.getTitle(),
                authorConverter.modelToDto(book.getAuthor()),
                genreConverter.modelToDto(book.getGenre()),
                commentConverter.modelToDto(book.getComments()));
    }


    public String dtoToString(BookDto bookDto) {
        return "Id: %d, title: %s, author: {%s}, genres: [%s], comments: {%s}".formatted(
                bookDto.getId(),
                bookDto.getTitle(),
                authorConverter.dtoToString(bookDto.getAuthorDto()),
                genreConverter.dtoToString(bookDto.getGenreDto()),
                commentConverter.dtoToString(bookDto.getCommentDtos()));
    }
}
