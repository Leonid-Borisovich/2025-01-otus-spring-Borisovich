package my.demo.websocket.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 https://www.dariawan.com/tutorials/spring/spring-boot-websocket-basic-example/
  Работает!!
 проверить:
 1) http://localhost:8080/websocket
 2) MainAppClient
 3) ws://localhost:8080/demo  через postman

 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
