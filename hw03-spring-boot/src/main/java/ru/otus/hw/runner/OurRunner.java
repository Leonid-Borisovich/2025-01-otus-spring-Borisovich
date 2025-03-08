package ru.otus.hw.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.service.TestRunnerService;

import static java.util.Objects.isNull;

@Component
public class OurRunner implements ApplicationRunner {

    private final TestRunnerService testRunnerService;

    public OurRunner(TestRunnerService testRunnerService) {
        this.testRunnerService = testRunnerService;
    }


    @Override
    public void run(ApplicationArguments args) {
        if (isNull(testRunnerService)) {
            System.err.println("Что-то у нас плохо с конфигурацией!");
            return;
        }
        testRunnerService.run();
    }
}

