package ru.otus.spring.rest;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.spring.domain.RealTimeEvent;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("fixed")
class EmulatorControllerTest {

    @LocalServerPort
    private int port = 7081;

    @Test
    void streamTest() {
        var client = WebClient.create(String.format("http://localhost:%d/emule", port));
        var expectedSize = 5;

        List<RealTimeEvent> result = client
                .get().uri("/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(RealTimeEvent.class)
                .take(expectedSize)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();

        //then
        assertThat(result).hasSize(expectedSize);
//
//                .contains("Пересечение периметра",
//                        String.format("id%s", 1),
//                        String.format("id%s", 2),
//                        String.format("id%s", 3),
//                        String.format("id%s", 4));
    }

}