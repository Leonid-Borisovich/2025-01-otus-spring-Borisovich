package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.DeviceDto;
import ru.otus.hw.models.Device;

@NoArgsConstructor
@Component
public class DeviceConverter {

    public DeviceDto modelToDto(Device device) {
        return new DeviceDto(device.getId(), device.getFullName());
    }



}
