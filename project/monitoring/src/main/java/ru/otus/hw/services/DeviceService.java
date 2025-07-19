package ru.otus.hw.services;

import ru.otus.hw.dto.DeviceDto;

import java.util.List;

public interface DeviceService {
    List<DeviceDto> findAll();
}
