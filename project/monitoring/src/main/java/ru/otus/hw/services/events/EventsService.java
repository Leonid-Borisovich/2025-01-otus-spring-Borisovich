package ru.otus.hw.services.events;

public interface EventsService {

    void stopEventLoop();

    void startEventsLoop(String host, String uri);
}
