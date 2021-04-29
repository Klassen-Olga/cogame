package de.cogame.globalhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GlobalHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalHandlerApplication.class, args);
	}

	public static String hello(){
		return "Hello Global 2";
	}
}
