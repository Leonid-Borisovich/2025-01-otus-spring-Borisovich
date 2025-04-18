package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.Author;

@NoArgsConstructor
@Component
public class AuthorConverter {
    public String dtoToString(Author author) {
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }

    public Author modelToDto(ru.otus.hw.models.Author author) {
        return new Author(author.getId(), author.getFullName());
    }



}
