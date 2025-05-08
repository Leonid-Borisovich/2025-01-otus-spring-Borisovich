package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.rest.BooksController;
import ru.otus.hw.controller.rest.JSBookDto;
import ru.otus.hw.dto.AurhorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(BooksController.class)
class BooksControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private AuthorService authorService;
    @MockitoBean
    private GenreService genreService;
    @MockitoBean
    private CommentService commentService;

    private List<BookDto> bookDtos = List.of(new BookDto(1, "War and Peace", new AurhorDto(), new GenreDto(), new ArrayList<CommentDto>()));

    @Test
    void shouldCorrectSaveNewBook() throws Exception {
        BookDto expectedBookDto = bookDtos.get(0);
        JSBookDto jsBookDto = new JSBookDto(null,"DemoTitle", 1L, 1L);
        when(bookService.insert("DemoTitle", 1L, 1L)).thenReturn(expectedBookDto);

        mvc.perform(post("/api/v1/book/")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(jsBookDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBookDto)));
    }

    @Test
    void shouldGetAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(bookDtos);
        mvc.perform(get("/api/v1/book/")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDtos)));
    }

    @Test
    void shouldGetBook() throws Exception {
        BookDto expectedBookDto = bookDtos.get(0);
        when(bookService.findById(1L)).thenReturn(Optional.of(expectedBookDto));
        mvc.perform(get("/api/v1/book/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBookDto)));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        BookDto expectedBookDto = bookDtos.get(0);
        when(bookService.findById(100L)).thenReturn(Optional.of(expectedBookDto));
        doNothing().when(bookService).deleteById(1);
        mvc.perform(delete("/api/v1/book/100"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted is success"));
        verify(bookService, times(1)).deleteById(100L);
    }


}