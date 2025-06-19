package ru.otus.hw.rest;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookReactRepository;
import ru.otus.hw.rest.dto.BookDto;

import java.util.List;

import static org.mockito.BDDMockito.given;


@SpringBootTest
class BookControllerTest {

    @Autowired
    private BooksController booksController;
    @Autowired
    private BookConverter bookConverter;


    private WebTestClient client;

    @MockBean
    private BookReactRepository bookReactRepository;

    @PostConstruct
    private void init(){
        client = WebTestClient.bindToController(booksController).build();
    }

    @Test
    void getBooks() {
        var bookList = List.of(new Book( "b_1050", "BookTitle_1050", new Author("a_1050", "Author_1050"), new Genre("g_1050", "Genre_1050")));
        given(bookReactRepository.findAll()).willReturn(Flux.fromIterable(bookList));

        client.get()
                .uri("/book/")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void getBook() {

        var aBook = new Book( "b_1050", "BookTitle_1050", new Author("a_1050", "Author_1050"), new Genre("g_1050", "Genre_1050"));
        var dto = bookConverter.modelToDto(aBook);
        given(bookReactRepository.findById(aBook.getId())).willReturn(Mono.just(aBook));
        client.get()
                .uri("/book/" + aBook.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(dto);
    }

}
