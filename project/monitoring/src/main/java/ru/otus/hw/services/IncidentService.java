package ru.otus.hw.services;

import ru.otus.hw.dto.IncidentDto;

import java.util.List;
import java.util.Optional;

public interface IncidentService {
    Optional<IncidentDto> findById(long id);

    List<IncidentDto> findAll();

    IncidentDto insert(String title, String deviceId);

    IncidentDto update(long id, String description, String deviceId);

    void deleteById(long id);
}
