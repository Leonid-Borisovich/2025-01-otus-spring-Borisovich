package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.ActionDto;
import ru.otus.hw.models.Action;
import ru.otus.hw.services.ActionTypeService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ActionConverter {

    private final ActionTypeService actionTypeService;

    public List<ActionDto> modelToDto(List<Action> entities) {
        return entities.stream().map(entity ->
                        new ActionDto(
                                entity.getId(),
                                entity.getWhatDo(),
                                entity.getIncidentId(),
                                actionTypeService.findById(entity.getActionTypeId()).map(t -> t.getName()).orElse(null)))
                .collect(Collectors.toList());
    }

    public ActionDto modelToDto(Action entity) {
        return new ActionDto(
                                entity.getId(),
                                entity.getWhatDo(),
                                entity.getIncidentId(),
                                actionTypeService.findById(entity.getActionTypeId()).map(t -> t.getName()).orElse(null));

    }

}
