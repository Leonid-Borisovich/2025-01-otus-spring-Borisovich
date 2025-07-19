package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.IncidentConverter;
import ru.otus.hw.dto.IncidentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Incident;
import ru.otus.hw.repositories.DeviceRepository;
import ru.otus.hw.repositories.IncidentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class IncidentServiceImpl implements IncidentService {
    private final DeviceRepository deviceRepository;

    private final IncidentRepository incidentRepository;

    private  final IncidentConverter incidentConverter;

    @Override
    @Transactional(readOnly = true)
    public Optional<IncidentDto> findById(long id) {
        return incidentRepository.findById(id).map(incidentConverter::modelToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncidentDto> findAll() {
        List<IncidentDto> list = incidentRepository.findAll().stream().map(incidentConverter::modelToDto).collect(Collectors.toList());
        log.info("Оперативная обстановка сейчас - {} инцидентов", list.size());
        return list;
    }

    @Override
    @Transactional
    public IncidentDto insert(String description, long deviceId) {
        Incident incident = save(0, description, deviceId);
        return incidentConverter.modelToDto(incident);
    }

    @Override
    @Transactional
    public IncidentDto update(long id, String description, long deviceId) {
        Incident incident = save(id, description, deviceId);
        return incidentConverter.modelToDto(incident);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        incidentRepository.deleteById(id);
    }

    private Incident save(long id, String description, long deviceId) {
        var device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device with id %d not found".formatted(deviceId)));
        var incident = new Incident(id, description, device);
        return incidentRepository.save(incident);
    }
}
