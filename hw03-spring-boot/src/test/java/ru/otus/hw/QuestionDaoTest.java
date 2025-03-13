package ru.otus.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionDaoTest {

    private final CsvQuestionDao questionDao;

    public QuestionDaoTest() {
        AppProperties appProps = new AppProperties();
        appProps.setRightAnswersCountToPass(1);
        appProps.setLocale("en-US");
        Map<String, String> map = new HashMap<>();
        map.put("en-US", "test_questions.csv");
        appProps.setFileNameByLocaleTag(map);
        this.questionDao = new CsvQuestionDao(appProps);
    }

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
