package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.rest.dto.AuthorDto;

@NoArgsConstructor
@Component
public class AuthorConverter {
    public String dtoToString(AuthorDto author) {
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }

    public AuthorDto modelToDto(ru.otus.hw.models.Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }



}
