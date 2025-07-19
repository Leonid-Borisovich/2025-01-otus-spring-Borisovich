package ru.otus.hw.services;

import ru.otus.hw.dto.ActionDto;
import ru.otus.hw.models.Action;

import java.util.List;

public interface ActionService {

    List<ActionDto> findAllForBook(long bookId);

    Action insert(String text, long incidentId);

    Action update(long id, String text, long incidentId);

    void deleteById(long id);

    void setAll(long incidentId, List<String> actions);
}
