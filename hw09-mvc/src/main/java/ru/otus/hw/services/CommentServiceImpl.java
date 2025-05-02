package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;


    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllForBook(long bookId) {
        return commentConverter.modelToDto(commentRepository.findAllByBookId(bookId));
    }

    @Override
    @Transactional
    public Comment insert(String text, long bookId) {
        return save(0, text, bookId);
    }

    @Override
    @Transactional
    public Comment update(long id, String text, long bookId) {
        return save(id, text, bookId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private Comment save(long id, String text, long bookId) {
        var comment  = new Comment(id, text, bookId);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void setAll(long bookId, List<String> comments){
        List<Comment> oldCommentList = commentRepository.findAllByBookId(bookId);
        commentRepository.deleteAll(oldCommentList);
        comments.stream().map(t -> new Comment(t, bookId)).forEach(t ->commentRepository.save(t));
    }
}
