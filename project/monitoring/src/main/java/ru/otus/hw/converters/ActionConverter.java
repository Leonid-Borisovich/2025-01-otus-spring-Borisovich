package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.ActionDto;
import ru.otus.hw.models.Action;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class ActionConverter {

    public List<ActionDto> modelToDto(List<Action> entities) {
        return entities.stream().map(entity -> new ActionDto(entity.getId(), entity.getWhatDo(), entity.getIncidentId()))
                .collect(Collectors.toList());
    }

}
