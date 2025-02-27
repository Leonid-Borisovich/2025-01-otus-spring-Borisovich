package ru.otus.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuestionDaoMockTest {

    @Mock
    private  TestFileNameProvider testFileNameProvider;

    @InjectMocks
    private  CsvQuestionDao questionDao;

    @Test
    void testMethod_findAll() {

        when(testFileNameProvider.getTestFileName()).thenReturn("test_questions.csv");

        List<Question> questions =  questionDao.findAll();
        Assertions.assertNotNull(questions);
        Assertions.assertFalse(questions.isEmpty());
        Assertions.assertEquals(1, questions.size());

        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("To be", true));
        answers.add(new Answer("Not to be", false));
        Question expectedQuestionToBe = new Question("To be or not to be - what is better?", answers);

        Assertions.assertTrue(questions.contains(expectedQuestionToBe));
    }
}
