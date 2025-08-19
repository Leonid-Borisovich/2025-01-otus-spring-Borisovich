package ru.otus.hw;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.events.RealTimeEvent;

import java.util.LinkedHashMap;

/**
 * Проверка работы эмулятора событий
 */
public class EmulatorClient {
    public static void main(String[] args) {

        String host = "http://localhost:7080";
        String uri = "/emule/events";

        //http://localhost:7081/emule/events

        WebClient webClient = WebClient.create(host);

        Flux<LinkedHashMap> result = webClient
                .get().uri( uri)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(LinkedHashMap.class);
        //     .doOnError(WebClientResponseException.class, e -> onResponseException(e));
        result.subscribe(t -> {
            ObjectMapper objectMapper = new ObjectMapper();
            RealTimeEvent ev = objectMapper.convertValue(t, RealTimeEvent.class);
            System.out.println("result: " + ev);
            // Process each event in the Flux
        });
        int i = 0;
        while (true && i < 10) {
            try {
                Thread.sleep(5000);
                i++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}