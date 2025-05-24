package ru.otus.hw.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.dto.AurhorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ClassicBooksController.class)
class ClassicBooksControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BookService bookService;
    @MockitoBean
    private BookConverter bookConverter;
    @MockitoBean
    private AuthorService authorService;
    @MockitoBean
    private GenreService genreService;
    @MockitoBean
    private CommentService commentService;


    private List<BookDto> bookDtos = List.of(new BookDto(1, "War and Peace", new AurhorDto(), new GenreDto(), new ArrayList<CommentDto>()));
    private List<AurhorDto> testAuthors = List.of(new AurhorDto(1, "Lev Tolstoj"));
    private List<GenreDto> testGenreDtos = new ArrayList<>();

      @Test
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        when(bookService.findAll()).thenReturn(bookDtos);
        mvc.perform(get("/"))
                .andExpect(view().name("list"));
    }

    @Test
    void shouldRenderEditPageWithCorrectViewAndModelAttributes() throws Exception {
        BookDto expectedBookDto = bookDtos.get(0);
        when(bookService.findById(1L)).thenReturn(Optional.of(expectedBookDto));

        mvc.perform(get("/edit/1"))
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("book", expectedBookDto));
    }

    @Test
    void shouldRenderErrorPageWhenBookNotFound() throws Exception {
        when(bookService.findById(1L)).thenThrow(new EntityNotFoundException());
        mvc.perform(get("/edit/1"))
                .andExpect(view().name("customError"));
    }

    @Test
    void shouldRenderAddBook() throws Exception {
        BookDto expectedBookDto = bookDtos.get(0);
        when(bookService.findById(1L)).thenReturn(Optional.of(expectedBookDto));
        when(authorService.findAll()).thenReturn(testAuthors);
        mvc.perform(get("/add"))
                .andExpect(view().name("add"))
                .andExpect(model().attribute("authors", testAuthors))
                .andExpect(model().attribute("genres", testGenreDtos));
    }

}