package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.Comment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class CommentConverter {
    public String dtoToString(List<Comment> comments) {
        return Arrays.toString(comments.toArray(new Comment[comments.size()]));
    }

    public List<Comment> modelToDto(List<ru.otus.hw.models.Comment> entities) {
        return entities.stream().map(entity -> new Comment(entity.getId(), entity.getText(), entity.getBookId()))
                .collect(Collectors.toList());
    }

}
