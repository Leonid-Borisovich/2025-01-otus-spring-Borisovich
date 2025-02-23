package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesConfig {

    private final Integer rightAnswersCountToPass;

    private final String testFileName;

    public PropertiesConfig(
            @Value("${test.rightAnswersCountToPass:}")
            int rightAnswersCountToPass,
            @Value("${test.fileName:}")
            final String testFileName) {

        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.testFileName = testFileName;

    }

    @Bean
    public AppProperties getAppProperties() {
        return new AppProperties(rightAnswersCountToPass, testFileName);
    }

}
