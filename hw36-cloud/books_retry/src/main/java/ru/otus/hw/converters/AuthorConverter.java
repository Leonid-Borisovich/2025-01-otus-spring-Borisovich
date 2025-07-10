package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AurhorDto;
import ru.otus.hw.models.Author;

@NoArgsConstructor
@Component
public class AuthorConverter {
    public String dtoToString(AurhorDto author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());
    }

    public AurhorDto modelToDto(Author author) {
        return new AurhorDto(author.getId(), author.getFullName());
    }



}
