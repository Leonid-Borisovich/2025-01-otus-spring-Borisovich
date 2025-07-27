package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
localhost:8080

localhost:8080/edit?id=1
localhost:8080/add

GET localhost:8080/actuator/health

GET localhost:8080/api/v1/incident/
PATCH localhost:8080/api/v1/incident/{id}

 */



@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
