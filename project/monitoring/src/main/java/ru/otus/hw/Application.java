package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
localhost:8080

localhost:8080/edit?id=1
localhost:8080/add

localhost:8080/actuator/health

localhost/api/v1/incident/


 */



@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
