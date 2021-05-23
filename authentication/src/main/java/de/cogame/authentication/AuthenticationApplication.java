package de.cogame.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

}
