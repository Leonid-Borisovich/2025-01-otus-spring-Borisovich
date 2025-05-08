package ru.otus.hw.controller.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSBookDto {

    private Long id;

    private String title;

    private Long authorId;

    private Long genreId;

}
