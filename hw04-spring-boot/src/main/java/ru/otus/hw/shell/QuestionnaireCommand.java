package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerService;

@RequiredArgsConstructor
@ShellComponent
public class QuestionnaireCommand {

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Start asking", key = {"ask"})
    public String doAsking() {
        testRunnerService.run();
        return String.format("Successfully asking, good bye!");
    }
}
