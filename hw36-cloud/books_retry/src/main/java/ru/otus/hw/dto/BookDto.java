package ru.otus.hw.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BookDto {
    private long id;

    private String title;

    @EqualsAndHashCode.Exclude
    private AurhorDto authorDto;

    @EqualsAndHashCode.Exclude
    private GenreDto genreDto;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<CommentDto> commentDtos;

    public BookDto(long id, String title, AurhorDto authorDto, GenreDto genreDto) {
        this.id = id;
        this.title = title;
        this.authorDto = authorDto;
        this.genreDto = genreDto;
        this.commentDtos = new ArrayList<>();
    }

    public String commentsAsString() {
        if (isEmpty(commentDtos)){
            return "";
        }
        return String.join(", ", commentDtos.stream().map(t -> t.getText()).collect(Collectors.toList()));
    }

}
