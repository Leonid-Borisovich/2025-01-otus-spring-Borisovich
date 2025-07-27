package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.DeviceConverter;
import ru.otus.hw.dto.DeviceDto;
import ru.otus.hw.repositories.DeviceRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository DeviceRepository;
    private final DeviceConverter DeviceConverter;

    @Override
    @Transactional(readOnly = true)
    public DeviceDto findById(String id) {
        return DeviceRepository.findById(id).map(DeviceConverter::modelToDto).orElse(null);
    }


    @Override
    @Transactional(readOnly = true)
    public List<DeviceDto> findAll() {
        return DeviceRepository.findAll().stream().map(DeviceConverter::modelToDto).collect(Collectors.toList());
    }
}
