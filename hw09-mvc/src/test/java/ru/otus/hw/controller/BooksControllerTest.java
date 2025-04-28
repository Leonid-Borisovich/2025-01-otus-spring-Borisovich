package ru.otus.hw.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.dto.Author;
import ru.otus.hw.dto.Book;
import ru.otus.hw.dto.Comment;
import ru.otus.hw.dto.Genre;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BooksController.class)
class BooksControllerTest {

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


    private List<Book> books = List.of(new Book(1, "War and Peace", new Author(), new Genre(), new ArrayList<Comment>()));
    private List<Author> testAuthors = List.of(new Author(1, "Lev Tolstoj"));
    private List<Genre> testGenres = new ArrayList<>();

      @Test
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        when(bookService.findAll()).thenReturn(books);
        mvc.perform(get("/"))
                .andExpect(view().name("list"))
                .andExpect(model().attribute("books", books));
    }

    @Test
    void shouldRenderEditPageWithCorrectViewAndModelAttributes() throws Exception {
        Book expectedBookDto = books.get(0);
        when(bookService.findById(1L)).thenReturn(Optional.of(expectedBookDto));

        mvc.perform(get("/edit").param("id", "1"))
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("book", expectedBookDto));
    }

    @Test
    void shouldRenderErrorPageWhenBookNotFound() throws Exception {
        when(bookService.findById(1L)).thenThrow(new EntityNotFoundException());
        mvc.perform(get("/edit").param("id", "1"))
                .andExpect(view().name("customError"));
    }

    @Test
    void shouldSaveBookAndRedirectToContextPath() throws Exception {
        Book expectedBookDto = books.get(0);
        when(bookService.findById(1L)).thenReturn(Optional.of(expectedBookDto));
        mvc.perform(post("/add")
                        .param("title", "Anna K.")
                        .param("authorId", "3")
                        .param("genreId", "3")
                   )
                .andExpect(view().name("redirect:/"));
        verify(bookService, times(1)).insert( "Anna K.", 3, 3);
    }

    @Test
    void shouldRenderAddBook() throws Exception {
        Book expectedBookDto = books.get(0);
        when(bookService.findById(1L)).thenReturn(Optional.of(expectedBookDto));
        when(authorService.findAll()).thenReturn(testAuthors);
        mvc.perform(get("/add"))
                .andExpect(view().name("add"))
                .andExpect(model().attribute("authors", testAuthors))
                .andExpect(model().attribute("genres", testGenres));
    }

}