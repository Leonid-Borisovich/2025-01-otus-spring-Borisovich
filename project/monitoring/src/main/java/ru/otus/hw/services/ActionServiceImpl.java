package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.ActionConverter;
import ru.otus.hw.dto.ActionDto;
import ru.otus.hw.models.Action;
import ru.otus.hw.repositories.ActionRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;
    private final ActionConverter actionConverter;


    @Override
    @Transactional(readOnly = true)
    public List<ActionDto> findAllForBook(long incidentId) {
        return actionConverter.modelToDto(actionRepository.findAllByIncidentId(incidentId));
    }

    @Override
    @Transactional
    public ActionDto insert(String text, long incidentId, long actionTypeId) {
        return actionConverter.modelToDto(save(0, text, incidentId, actionTypeId));
    }

    @Override
    @Transactional
    public ActionDto update(long id, String text, long incidentId, long actionTypeId) {
        return actionConverter.modelToDto(save(id, text, incidentId, actionTypeId));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        actionRepository.deleteById(id);
    }

    private Action save(long id, String text, long incidentId, long actionTypeId) {
        var Action  = new Action(id, text, incidentId, actionTypeId);
        return actionRepository.save(Action);
    }

}
