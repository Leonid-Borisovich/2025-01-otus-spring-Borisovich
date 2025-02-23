package ru.otus.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestServiceTest {

    @Mock
    private  IOService ioService;

    @Mock
    private  QuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl testService;

    @Test
    void testExecuteForStudent() {

        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("To be", true));
        answers.add(new Answer("Not to be", false));
        Question mockQuestionToBe = new Question("To be or not to be - what is better?", answers);
        List<Question> questions = new ArrayList<>();
        questions.add(mockQuestionToBe);

        Student student = new Student("Vasj", "Pupkin");

        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readIntForRangeWithPrompt(1, 2, "Your answer? Please, input the number:",
                "Invalid number! Input again!")).thenReturn(1);
        TestResult testResult = testService.executeTestFor(student);

        Assertions.assertTrue( 1 == testResult.getRightAnswersCount());
    }
}
