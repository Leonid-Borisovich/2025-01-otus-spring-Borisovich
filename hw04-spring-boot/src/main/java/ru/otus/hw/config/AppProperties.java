package ru.otus.hw.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@AllArgsConstructor
@Setter
@ConfigurationProperties(prefix = "test")
public class AppProperties implements TestConfig, TestFileNameProvider {

    @Getter
    private int rightAnswersCountToPass;

    @Getter
    private String testFileName;

}
