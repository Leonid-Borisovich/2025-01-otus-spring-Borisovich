package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.Genre;

@NoArgsConstructor
@Component
public class GenreConverter {
    public String dtoToString(Genre genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }

    public Genre modelToDto(ru.otus.hw.models.Genre entity) {
        return new Genre(entity.getId(), entity.getName());
    }

}
