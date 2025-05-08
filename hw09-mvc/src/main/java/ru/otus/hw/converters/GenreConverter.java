package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;

@NoArgsConstructor
@Component
public class GenreConverter {
    public String dtoToString(GenreDto genreDto) {
        return "Id: %d, Name: %s".formatted(genreDto.getId(), genreDto.getName());
    }

    public GenreDto modelToDto(Genre entity) {
        return new GenreDto(entity.getId(), entity.getName());
    }

}
