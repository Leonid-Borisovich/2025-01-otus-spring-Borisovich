package ru.otus.hw.services;

import ru.otus.hw.dto.AurhorDto;

import java.util.List;

public interface AuthorService {
    List<AurhorDto> findAll();
}
