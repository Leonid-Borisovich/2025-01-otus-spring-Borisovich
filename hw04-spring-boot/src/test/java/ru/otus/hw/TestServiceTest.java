package ru.otus.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestService;
import ru.otus.hw.service.TestServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TestServiceImpl.class})
public class TestServiceTest {

    @MockitoBean
    private  IOService ioService;

    @MockitoBean
    private  QuestionDao questionDao;

    @Autowired
    private TestService testService;

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
        when(ioService.readIntForRangeWithPrompt(anyInt(),  anyInt(), anyString(), anyString())).thenReturn(1);
        TestResult testResult = testService.executeTestFor(student);

        Assertions.assertTrue( 1 == testResult.getRightAnswersCount());
        verify(ioService, times(1)).readIntForRangeWithPrompt(1, 2, "Your answer? Please, input the number:",
                "Invalid number! Input again!");
    }
}
