package ru.otus.hw.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BooksController {

    private final BookService bookService;
    private final CommentService commentService;



    @GetMapping("/api/v1/book/")
    public List<BookDto> listPage() {
        List<BookDto> bookDtos = bookService.findAll().stream()
                .collect(Collectors.toList());
        if (bookDtos.isEmpty()) {
            throw new EntityNotFoundException("Books not found!");
        }
        return bookDtos;
    }

    @GetMapping("/api/v1/book/{id}")
    public BookDto getBook(@PathVariable(value = "id", required = true) long id) {
        return  bookService.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity deleteBook(@PathVariable(value = "id", required = true) long id) {
        bookService.findById(id).orElseThrow(EntityNotFoundException::new);
        bookService.deleteById(id);
        return ResponseEntity.ok("Deleted is success");
    }

    @PutMapping("/api/v1/book/{id}")
    public BookDto updatePerson(
                             @PathVariable(value = "id", required = true) long id,
                             @Valid
                             @RequestBody
                             JSBookDto jsBookDto,
                             String rawText
    ) {
        BookDto bookUpdated = bookService.update(id, jsBookDto.getTitle(), jsBookDto.getAuthorId(), jsBookDto.getGenreId());
        List<String> updatedComments = new ArrayList<>(Arrays.asList(rawText.split("\n")));
        commentService.setAll(id, updatedComments);
        return bookUpdated;

    }

    @PostMapping("/api/v1/book/")
    public BookDto addNewBook(
            @Valid
            @RequestBody
            JSBookDto JSBookDto) {
        BookDto newbook = bookService.insert(JSBookDto.getTitle(), JSBookDto.getAuthorId(), JSBookDto.getGenreId());
        return newbook;
    }

}
