package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.rest.dto.CommentDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class CommentConverter {
    public String dtoToString(List<CommentDto> comments) {
        return Arrays.toString(comments.toArray(new CommentDto[comments.size()]));
    }

    public List<CommentDto> modelToDto(List<ru.otus.hw.models.Comment> entities) {
        return entities.stream().map(entity -> new CommentDto(entity.getId(), entity.getText()))
                .collect(Collectors.toList());
    }

}
