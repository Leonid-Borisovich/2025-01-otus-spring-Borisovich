package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.AurhorDto;
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
        List<BookDto> bookDtos = bookService.findAll().stream()
                .collect(Collectors.toList());
        model.addAttribute("books", bookDtos);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        BookDto bookDto = bookService.findById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("book", bookDto);
        List<AurhorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genres", genreDtos);
        List<CommentDto> comments = commentService.findAllForBook(id);

        String commentJoin = comments.stream().map(c -> c.getText()).collect(Collectors.joining("\n"));
        model.addAttribute("comments", commentJoin);
        return "edit";
    }

    @PostMapping("/edit")
    public String saveBook(BookDto bookDto,
                             @RequestParam(value = "authorId") Long authorId,
                             @RequestParam(value = "genreId") Long genreId,
                             String rawText
    ) {
        bookService.update(bookDto.getId(), bookDto.getTitle(), authorId, genreId);
        List<String> updatedComments = new ArrayList<>(Arrays.asList(rawText.split("\n")));
        commentService.setAll(bookDto.getId(), updatedComments);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        bookService.findById(id).orElseThrow(EntityNotFoundException::new);
        bookService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        List<AurhorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        List<GenreDto> genreDtos = genreService.findAll();
        model.addAttribute("genres", genreDtos);
        return "add";
    }

    @PostMapping("/add")
    public String saveNewBook(String title,
                             @RequestParam(value = "authorId") Long authorId,
                             @RequestParam(value = "genreId") Long genreId) {
        bookService.insert(title, authorId, genreId);
        return "redirect:/";
    }

}
