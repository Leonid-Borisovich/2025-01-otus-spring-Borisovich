package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Device;

public interface DeviceRepository extends JpaRepository<Device, String> {

}
