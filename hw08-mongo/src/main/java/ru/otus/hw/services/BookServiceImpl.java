package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private  final BookConverter bookConverter;

    @Override
    public Optional<ru.otus.hw.dto.Book> findById(String id) {
        return bookRepository.findById(id).map(bookConverter::modelToDto);
    }

    @Override
    public List<ru.otus.hw.dto.Book> findAll() {
        return bookRepository.findAll().stream().map(bookConverter::modelToDto).collect(Collectors.toList());
    }

    @Override
    public Book insert(String title, String authorId, String genreId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %s not found".formatted(genreId)));
        var book = new Book(title, author, genre);
        return bookRepository.save(book);

    }

    @Override
    public Book update(String id, String title, String authorId, String genreId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %s not found".formatted(genreId)));
        var book = new Book(id, title, author, genre);
        return bookRepository.save(book);
    }

    @Override
    public Book addComment(String bookId, String text){
        var book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(bookId)));
        book.getComments().add(new Comment(text));
        return bookRepository.save(book);
    }

    @Override
    public Book delComment(String bookId, String commentId){
        var book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(bookId)));
        book.setComments(book.getComments().stream().filter(t->!commentId.equals(t.getId())).toList());
        return bookRepository.save(book);
    }


    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }
}
