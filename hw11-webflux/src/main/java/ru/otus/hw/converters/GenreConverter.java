package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.rest.dto.GenreDto;

@NoArgsConstructor
@Component
public class GenreConverter {
    public String dtoToString(GenreDto genre) {
        return "Id: %s, Name: %s".formatted(genre.getId(), genre.getName());
    }

    public GenreDto modelToDto(ru.otus.hw.models.Genre entity) {
        return new GenreDto(entity.getId(), entity.getName());
    }

}
