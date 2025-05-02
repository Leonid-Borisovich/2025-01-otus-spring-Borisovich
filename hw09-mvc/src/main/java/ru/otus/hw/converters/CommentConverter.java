package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class CommentConverter {
    public String dtoToString(List<CommentDto> commentDtos) {
        return Arrays.toString(commentDtos.toArray(new CommentDto[commentDtos.size()]));
    }

    public List<CommentDto> modelToDto(List<Comment> entities) {
        return entities.stream().map(entity -> new CommentDto(entity.getId(), entity.getText(), entity.getBookId()))
                .collect(Collectors.toList());
    }

}
