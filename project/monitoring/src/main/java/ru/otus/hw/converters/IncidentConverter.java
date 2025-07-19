package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.IncidentDto;
import ru.otus.hw.models.Incident;

@RequiredArgsConstructor
@Component
public class IncidentConverter {
    private final DeviceConverter deviceConverter;


    private final ActionConverter actionConverter;

    public IncidentDto modelToDto(Incident incident) {

        return new IncidentDto(incident.getId(), incident.getDescription(),
                deviceConverter.modelToDto(incident.getDevice()),
                actionConverter.modelToDto(incident.getActions()));
    }

}
