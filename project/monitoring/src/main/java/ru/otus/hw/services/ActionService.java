package ru.otus.hw.services;

import ru.otus.hw.dto.ActionDto;

import java.util.List;

public interface ActionService {

    List<ActionDto> findAllForBook(long bookId);

    ActionDto insert(String text, long incidentId, long actionTypeId);

    ActionDto update(long id, String text, long incidentId, long actionTypeId);

    void deleteById(long id);

}
