package ru.otus.hw.services.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import ru.otus.hw.dto.DeviceDto;
import ru.otus.hw.dto.IncidentDto;
import ru.otus.hw.models.events.RealTimeEvent;
import ru.otus.hw.services.DeviceService;
import ru.otus.hw.services.IncidentService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventsServiceImpl implements EventsService {

    private final  WebClient webClient;
    private final ObjectMapper objectMapper;
    private final DeviceService deviceService;
    private final IncidentService incidentService;

    private boolean isStart = false;
    private Disposable disposable = null;

    private static final String DEMO_MODE_EVENT = "Обнаружен автономер";  //"EventDescription"
    private static final String KEEP_ALIVE = "KeepAlive"; //"Comment"

    @Override
    public void startEventsLoop(String host, String uri) {
        synchronized (this) {
            if (isStart) {
                log.info("I am all ready working!");
                return;
            }
            isStart = true;
        }
        initClient(host, uri);
    }

    @Override
    public void stopEventLoop() {
        synchronized (this) {
            isStart = false;
        }
        disposeDisposable();
    }

    private void disposeDisposable() {
        if (Objects.nonNull(disposable)) {
            try {
                disposable.dispose();
            } catch (Exception e) {
                log.error("Error in dispose disposable, {}", e.getMessage());
            }
            disposable = null;
        }
    }

    private void initClient(String host, String uri) {
        log.info("Start connect and subscribe to the server: {}{}!", host, uri);
        while (Objects.isNull(disposable) && isStart) {
            Disposable disposable;
            Instant start = Instant.now();
            try {
                disposable = webClient
                        .get().uri(host + uri)
                        .accept(MediaType.TEXT_EVENT_STREAM)
                        .retrieve()
                        .bodyToFlux(LinkedHashMap.class)
//                        .bodyToFlux(LinkedHashMap.class)
                        .subscribe(
                                data -> {
                                    //logSuccess(host + uri, data);
                                    sendSuccess(data);
                                    },
                                error -> {
                                    //logError(host + uri, error);
                                    log.error(error.getMessage());
                                }
                        );
                this.disposable = disposable;
                return;
            } catch (Exception e) {
                log.error(host + uri, e.getMessage());
                Instant end = Instant.now();
                Instant deadline = start.plus(1, ChronoUnit.MINUTES);
                if (end.isBefore(deadline)) {
                    try {
                        synchronized (this) {
                            wait(deadline.toEpochMilli() - end.toEpochMilli());
                        }
                    } catch (InterruptedException ex) {
                        log.error("Error in wait timeout to reconnect to the server: {}", e.getMessage());
                    }
                }
            }
        }
    }

    private void sendSuccess(LinkedHashMap data) {
        RealTimeEvent realTimeEvent;
        try {
            realTimeEvent = objectMapper.convertValue(data, RealTimeEvent.class);
        } catch (Exception e) {
            log.error("Error in convert data to RealTimeEvent: {}", e.getMessage());
            return;
        }
        if (!StringUtils.hasText(realTimeEvent.getChannelId())) {
            log.warn("ChannelId is empty, may be Your response is bad! I don`t work with this events: {}" + realTimeEvent);
            return;
        }
        DeviceDto deviceDto = deviceService.findById(realTimeEvent.getChannelId());
        if (deviceDto == null) {
            log.warn("ChannelId is bad! I don`t work with this events: {}" + realTimeEvent);
            return;
        }

        IncidentDto incidentDto = incidentService.insert(realTimeEvent.getEventDescription(), realTimeEvent.getChannelId());
        log.info(" Incident {} inserted successfull!" + incidentDto);
//        String eventJsonString;
//        try {
//            eventJsonString = objectMapper.writeValueAsString(realTimeEvent);
//        } catch (JsonProcessingException e) {
//            log.error("Error by parsing RealTimeEvent!, {}", e.getMessage());
//            return;
//        }
    }

}
