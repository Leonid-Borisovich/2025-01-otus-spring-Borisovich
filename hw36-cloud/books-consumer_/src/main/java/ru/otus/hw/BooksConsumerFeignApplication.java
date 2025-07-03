package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//http://localhost:81/get-books/


@SpringBootApplication
public class BooksConsumerFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksConsumerFeignApplication.class, args);
	}

}
