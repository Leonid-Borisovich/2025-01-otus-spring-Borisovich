package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//http://localhost:8080
//http://localhost:8080/edit?id=1
//http://localhost:8080/edit?id=111
// http://localhost:8080/api/v1/book/


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
