package ru.otus.hw.services;

import ru.otus.hw.models.ActionType;

import java.util.Optional;

public interface ActionTypeService {
    Optional<ActionType> findById(long id);

}
