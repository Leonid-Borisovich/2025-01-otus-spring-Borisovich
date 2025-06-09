package ru.otus.hw.actuator;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.hw.services.BookService;

@Component
public class LibrarySizeHealthIndicator implements HealthIndicator {

    private final BookService bookService;

    public LibrarySizeHealthIndicator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public Health health() {
        int size  = bookService.findAll().size();
        if (size < 4) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("claim", "Я у вас уже все прочитал, мало книг!")
                    .build();
        } else {
            return Health.up().withDetail("thanksgiving", "Спасибо за новые книги!").build();
        }
    }
}
