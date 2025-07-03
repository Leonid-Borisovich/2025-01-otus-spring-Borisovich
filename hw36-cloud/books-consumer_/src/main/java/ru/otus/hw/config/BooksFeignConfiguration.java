package ru.otus.hw.config;

import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BooksFeignConfiguration {

    @Bean
    BooksFeignClient createBooksClient(ApplicationContext applicationContext) {
        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
        FeignClientBuilder.Builder<BooksFeignClient> builder =
                feignClientBuilder
                        .forType(BooksFeignClient.class, "someBooksClient" );
            builder.url("localhost:8080");
        return builder.build();
    }

}
