package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.ActionDto;
import ru.otus.hw.dto.IncidentDto;
import ru.otus.hw.models.Incident;
import ru.otus.hw.services.ActionService;

import java.util.List;


@RequiredArgsConstructor
@Component
public class IncidentConverter {
    private final DeviceConverter deviceConverter;


    private final ActionConverter actionConverter;
    private final ActionService actionService;

    public IncidentDto modelToDto(Incident incident) {

        List<ActionDto> actions = actionService.findAllForIncident(incident.getId());

        return new IncidentDto(incident.getId(), incident.getDescription(),
                deviceConverter.modelToDto(incident.getDevice()),
                actions);
    }

}
