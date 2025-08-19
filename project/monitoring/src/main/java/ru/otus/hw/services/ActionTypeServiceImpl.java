package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.ActionType;
import ru.otus.hw.repositories.ActionTypeRepository;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ActionTypeServiceImpl implements ActionTypeService {

    private final ActionTypeRepository actionTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<ActionType> findById(long id) {
        return actionTypeRepository.findById(id);
    }

}
