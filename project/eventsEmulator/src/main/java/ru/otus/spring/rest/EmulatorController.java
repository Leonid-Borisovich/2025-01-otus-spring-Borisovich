package ru.otus.spring.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.domain.RealTimeEvent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * http://localhost:7081/emule/events
 * Эмулятор сервера Микроскоп.
 *
 */
@RequestMapping("/emule")
@RestController
public class EmulatorController {
    private static final Logger logger = LoggerFactory.getLogger(EmulatorController.class);

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping(path = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<RealTimeEvent> genEvents() {
        logger.info("I write events");
        return Flux.generate(() -> 0, (state, emitter) -> {
                    if (state < 5) { // шлем 5 раз
                        emitter.next(state); // Отправляем элемент в поток
                        state++;
                    } else {
                        emitter.complete();  // Завершаем поток, если достигли конца диапазона
                    }
                    return state;
                })
                .delayElements(Duration.ofSeconds(10L))
                .map(t ->  {
                    LocalDateTime nowDate = LocalDateTime.now();
                    String now = FORMATTER.format(nowDate);
                    RealTimeEvent event =  new RealTimeEvent("id_" + t.toString(),
                            now,
                            String.format("Пересечение периметра %s: %s", t, now),
                            "ID_Южные_ворота",
                            "Южные ворота",
                            "Пересечение периметра на Южных воротах"
                    );
                    return event;
                });
    }

}
