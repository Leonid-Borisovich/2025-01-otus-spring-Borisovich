package ru.otus.hw.config;


import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.BookDto;

import java.util.List;

@FeignClient(
//        name = "books-producer",
//        url = "http://localhost:8080/"
        //,fallback = BooksFeignClient.BooksClientFallback.class
)
@Headers({"Content-type", "application/json"})
public interface BooksFeignClient {

    @GetMapping("/api/v1/book/")
    public List<BookDto> getBooksDtosOverFeign();

//    @Component
//    public static class BooksClientFallback implements BooksCircuitController {
//
//        private final CircuitBooksService circuitBooksService;
//
//        public BooksClientFallback(CircuitBooksService circuitBooksService) {
//            this.circuitBooksService = circuitBooksService;
//        }
//
//        @Override
//        @GetMapping("/get-books/")
//        public String listPage(Model model) {
//            model.addAttribute("books", circuitBooksService.getTmpBooks());
//            return "list";
//        }
//    }
}
