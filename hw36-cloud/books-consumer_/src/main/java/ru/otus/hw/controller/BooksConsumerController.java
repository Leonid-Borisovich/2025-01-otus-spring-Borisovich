package ru.otus.hw.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.config.BooksFeignClient;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.CircuitBooksService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BooksConsumerController {

    private final BooksFeignClient booksFeignClient;

    private final CircuitBooksService circuitBooksService;


   // @Retry(name = "demoBreaker", fallbackMethod = "failsListPage")
    @CircuitBreaker(name = "demoBreaker", fallbackMethod = "failsListPage")
    @GetMapping("/get-books/")
    public String listPage(Model model) {
        List<BookDto> bookDtos = booksFeignClient.getBooksDtosOverFeign();
        model.addAttribute("books", bookDtos);
        return "list";
    }


    public String failsListPage(Model model) {
        List<BookDto> bookDtos = circuitBooksService.getTmpBooks();
        model.addAttribute("books", bookDtos);
        return "list";
    }


}
