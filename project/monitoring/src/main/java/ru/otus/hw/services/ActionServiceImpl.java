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
    private final ActionRepository ActionRepository;
    private final ActionConverter ActionConverter;


    @Override
    @Transactional(readOnly = true)
    public List<ActionDto> findAllForBook(long incidentId) {
        return ActionConverter.modelToDto(ActionRepository.findAllByIncidentId(incidentId));
    }

    @Override
    @Transactional
    public Action insert(String text, long incidentId) {
        return save(0, text, incidentId);
    }

    @Override
    @Transactional
    public Action update(long id, String text, long incidentId) {
        return save(id, text, incidentId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        ActionRepository.deleteById(id);
    }

    private Action save(long id, String text, long incidentId) {
        var Action  = new Action(id, text, incidentId);
        return ActionRepository.save(Action);
    }

    @Override
    @Transactional
    public void setAll(long incidentId, List<String> Actions){
        List<Action> oldActionList = ActionRepository.findAllByIncidentId(incidentId);
        ActionRepository.deleteAll(oldActionList);
        Actions.stream().map(t -> new Action(t, incidentId)).forEach(t ->ActionRepository.save(t));
    }
}
