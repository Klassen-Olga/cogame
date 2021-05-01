package de.cogame.userservice;



import com.example.multimodule.service.exception.CustomExceptionHandler;
import com.example.multimodule.service.security.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CustomExceptionHandler.class, SecurityConfiguration.class})
public class UserServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserServiceApplication.class, args);
	}

}
