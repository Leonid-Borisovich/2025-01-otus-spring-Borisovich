package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.repositories.AuthorReactRepository;
import ru.otus.hw.rest.dto.AuthorDto;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorReactRepository authorRepository;
    private final AuthorConverter authorConverter;

    @RequestMapping("/author/")
    public Flux<AuthorDto> getAuthors(){
        return authorRepository.findAll().map(authorConverter::modelToDto);

    }

}
