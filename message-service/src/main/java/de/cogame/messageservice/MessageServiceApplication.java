package de.cogame.messageservice;

import de.cogame.globalhandler.exception.CustomExceptionHandler;
import de.cogame.globalhandler.security.SecurityConfiguration;
import de.cogame.globalhandler.swagger.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CustomExceptionHandler.class, SecurityConfiguration.class, SwaggerConfig.class})
public class MessageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageServiceApplication.class, args);
	}

}
