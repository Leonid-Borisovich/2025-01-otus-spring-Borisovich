package ru.otus.hw.dao;

import ru.otus.hw.dao.dto.QuestionDto;
import java.util.List;

public interface QuestionDao {
    List<QuestionDto> findAll();
}
