package de.cogame.userservice;

import de.cogame.globalhandler.exception.CustomExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CustomExceptionHandler.class)
public class UserServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserServiceApplication.class, args);
	}

}
