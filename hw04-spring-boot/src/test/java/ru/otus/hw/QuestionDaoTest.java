package ru.otus.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class QuestionDaoTest {

    @Autowired
    private  TestFileNameProvider testFileNameProvider;

    @Autowired
    private QuestionDao questionDao;

    @Test
    void testMethod_findAll() {

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
