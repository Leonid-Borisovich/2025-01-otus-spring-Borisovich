package ru.otus.hw.task;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.hw.services.events.EventsService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {
    private final ScheduledExecutorService executorService =
            Executors.newSingleThreadScheduledExecutor();

    private final EventsService eventsService;

    @Value("${listen.to.host}")
    private String host;
    @Value("${listen.to.endpoint}")
    private String endpoint;

    public ScheduledTasks(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @PostConstruct
    public void init() {
        executorService.schedule(() -> startListen(host, endpoint), 1, TimeUnit.MINUTES);

        executorService.schedule(this::stopListen, 4, TimeUnit.MINUTES);
    }

    public void startListen(String host, String uri) {
        System.out.println("Прием событий запущен");
        eventsService.startEventsLoop(host, uri);
    }

    public void stopListen() {
        System.out.println("Прием событий остановлен через 3 минут");
        eventsService.stopEventLoop();
    }
}
