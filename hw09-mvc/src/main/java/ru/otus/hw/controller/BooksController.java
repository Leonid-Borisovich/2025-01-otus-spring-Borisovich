package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.dto.Genre;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.dto.Book;
import ru.otus.hw.dto.Author;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;


    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = bookService.findAll().stream()
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        Book book = bookService.findById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("book", book);
        List<Author> authors = authorService.findAll().stream().collect(Collectors.toList());
        model.addAttribute("authors", authors);
        List<Genre> genres = genreService.findAll().stream().collect(Collectors.toList());
        model.addAttribute("genres", genres);
        List<ru.otus.hw.models.Comment> comments = commentService.findAllForBook(id).stream().collect(Collectors.toList());

        String commentJoin = comments.stream().map(c -> c.getText()).collect(Collectors.joining("\n"));
        model.addAttribute("comments", commentJoin);
        return "edit";
    }

    @PostMapping("/edit")
    public String savePerson(Book book,
                             @RequestParam(value = "authorId") Long authorId,
                             @RequestParam(value = "genreId") Long genreId,
                             String rawText
    ) {
        bookService.update(book.getId(), book.getTitle(), authorId, genreId);
        List<String> updatedComments = new ArrayList<>(Arrays.asList(rawText.split("\n")));
        commentService.setAll(book.getId(), updatedComments);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        Book book = bookService.findById(id).orElseThrow(EntityNotFoundException::new);
        bookService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        List<Author> authors = authorService.findAll().stream().collect(Collectors.toList());
        model.addAttribute("authors", authors);
        List<Genre> genres = genreService.findAll().stream().collect(Collectors.toList());
        model.addAttribute("genres", genres);
        return "add";
    }

    @PostMapping("/add")
    public String saveNewPerson(String title,
                             @RequestParam(value = "authorId") Long authorId,
                             @RequestParam(value = "genreId") Long genreId) {
        bookService.insert(title, authorId, genreId);
        return "redirect:/";
    }

}
