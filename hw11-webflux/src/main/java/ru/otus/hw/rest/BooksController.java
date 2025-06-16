package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorReactRepository;
import ru.otus.hw.repositories.GenreReactRepository;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookReactRepository;
import ru.otus.hw.repositories.BookRepository;

@RequiredArgsConstructor
@RestController
public class BooksController {

    private final BookReactRepository bookReactRepository;
    private final BookConverter bookConverter;
    private final BookRepository bookRepository;
    private final AuthorReactRepository authorReactRepository;
    private final GenreReactRepository genreReactRepository;



    @GetMapping("/book/")
    public Flux<BookDto> listPage() {
        return bookReactRepository.findAll()
                .map(book -> bookConverter.modelToDto(book));
    }

    @GetMapping("/book/{id}")
    public Mono<ResponseEntity<BookDto>> getBook(@PathVariable(value = "id", required = true) String id) {
        return  bookReactRepository.findById(id)
                .map(book -> ResponseEntity.ok(bookConverter.modelToDto(book)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/book/{id}")
    public Mono<ResponseEntity<String>> deleteBook(@PathVariable(value = "id", required = true) String id) {
        Mono<Void> res  = bookReactRepository.deleteById(id);
        return res.map(t -> ResponseEntity.ok("Success deleted"))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/book/{id}")
    public Mono<ResponseEntity<BookDto>> update(@PathVariable String id, @RequestBody BookDto bookDto) {
        return Mono.zip(authorReactRepository.findById(bookDto.getAuthor().getId()),
                        genreReactRepository.findById(bookDto.getGenre().getId()),
                        bookReactRepository.findById(id))
                .flatMap(zip -> {
                    Author author = zip.getT1();
                    Genre genre = zip.getT2();
                    Book book = zip.getT3();
                    book.setAuthor(author);
                    book.setGenre(genre);
                    return bookReactRepository.save(book);
                })
                .map(book -> ResponseEntity.ok().body(bookConverter.modelToDto(book)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/book/")
    public Mono<ResponseEntity<BookDto>> addNewBook(
            @RequestBody
            BookDto bookDto) {
        return Mono.zip(authorReactRepository.findById(bookDto.getAuthor().getId()),
                        genreReactRepository.findById(bookDto.getGenre().getId()),
                        bookReactRepository.findById(bookDto.getId()))
                .flatMap(zip -> {
                    Author author = zip.getT1();
                    Genre genre = zip.getT2();
                    Book book = new Book(null, bookDto.getTitle(), author, genre);
                    return bookReactRepository.save(book);
                })
                .map(book -> ResponseEntity.ok().body(bookConverter.modelToDto(book)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
