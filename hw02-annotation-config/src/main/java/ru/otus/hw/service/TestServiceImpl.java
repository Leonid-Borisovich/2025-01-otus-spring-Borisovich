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

    private final IOService ioService;

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
            int answerNumber = ioService.readIntForRangeWithPrompt(
                    1,
                    orderedAnswerList.size(),
                    "Your answer? Please, input the number:",
                    "Invalid number! Input again!");
            var isAnswerValid = orderedAnswerList.get(answerNumber - 1).isCorrect();

            testResult.applyAnswer(question, isAnswerValid);

            ioService.printLine("-------------------------------------------");
         }
        return testResult;
    }

    private void printGreeting(Student student) {
        ioService.printLine("Dear " + student.getFullName() + "!");
        ioService.printFormattedLine("Please answer the questions below%n");
    }


    private void printQuestion(String text) {
        ioService.printLine("New q0uestion:");
        ioService.printLine(text);
    }

    private void printAnswers(List<Answer> answerList) {
        ioService.printLine("Answers:");
        for (int i = 0; i < answerList.size(); i++) {
            printAnswerChoice(i + 1, answerList.get(i).text());
        }
    }

    private void printAnswerChoice(int number, String text) {
        ioService.printFormattedLine("%d. %s", number, text);
    }

}
