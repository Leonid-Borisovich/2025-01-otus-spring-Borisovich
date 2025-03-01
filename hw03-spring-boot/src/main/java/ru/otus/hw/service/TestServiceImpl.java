package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        printGreeting(student);
        var testResult = new TestResult(student);

        var questions = questionDao.findAll();
        for (var question: questions) {
            List<Answer> orderedAnswerList = new ArrayList(question.answers());

            printQuestion(question.text());
            printAnswers(orderedAnswerList);
            int answerNumber = ioService.readIntForRangeWithPromptLocalized(
                    1,
                    orderedAnswerList.size(),
                    "TestService.get.answer",
                    "TestService.get.answer.again");
            var isAnswerValid = orderedAnswerList.get(answerNumber - 1).isCorrect();

            testResult.applyAnswer(question, isAnswerValid);

            ioService.printLine("-------------------------------------------");
         }
        return testResult;
    }

    private void printGreeting(Student student) {
        ioService.printFormattedLineLocalized("ResultService.student", student.getFullName());
        ioService.printLineLocalized("TestService.answer.the.questions");
    }


    private void printQuestion(String text) {
        ioService.printLine(text);
    }

    private void printAnswers(List<Answer> answerList) {
        for (int i = 0; i < answerList.size(); i++) {
            printAnswerChoice(i + 1, answerList.get(i).text());
        }
    }

    private void printAnswerChoice(int number, String text) {
        ioService.printFormattedLine("%d. %s", number, text);
    }

}
