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

/**
 * http://localhost:7081/emule/events
 * Эмулятор сервера Микроскоп.
 *
 */
@RequestMapping("/emule")
@RestController
public class EmulatorController {
    private static final Logger logger = LoggerFactory.getLogger(EmulatorController.class);
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
                .delayElements(Duration.ofSeconds(2L))
                .map(t ->  {

                    RealTimeEvent e =  new RealTimeEvent("id" + t.toString(),
                            "19.10.2022 09:58:55",
                            "Пересечение периметра",
                            "e0391a80-c921-4ffc-9a69-107fcf28e34e",
                            "Камера 3",
                            String.format("Пересечение периметра:%s", t)
                    );
                    return e;
                });
    }

}
