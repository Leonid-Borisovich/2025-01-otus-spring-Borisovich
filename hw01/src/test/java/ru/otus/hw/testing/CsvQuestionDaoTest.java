package ru.otus.hw.testing;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.dao.dto.QuestionDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ExtendWith(DaoParameterResolver.class)
public class CsvQuestionDaoTest {

    private final QuestionDao questionDao;

    @Test
    void testMethod_findAll() {

        List<QuestionDto> questionDtos =  questionDao.findAll();
        Assertions.assertNotNull(questionDtos);
        Assertions.assertFalse(questionDtos.isEmpty());
        Assertions.assertEquals(5, questionDtos.size());

        Assertions.assertEquals(2, questionDtos.stream()
                .filter(q -> q.getText().contains("To be or not to be"))
                .collect(Collectors.toSet())
                .size());
    }
}
