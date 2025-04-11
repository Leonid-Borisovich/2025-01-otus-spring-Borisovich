package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.services.CommentService;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find all comment for book", key = "acforb")
    public String findAllCommentForBookId(long bookId) {
        return commentConverter.dtoToString(
                commentConverter.modelToDto(
                        commentService.findAllForBook(bookId)));

    }

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById(long id) {
        return commentService.findById(id).map(ru.otus.hw.models.Comment::getText).orElse("Comment with id %d not found".formatted(id));
    }
}
