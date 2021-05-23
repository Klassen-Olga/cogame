package de.cogame.messageservice;

import de.cogame.globalhandler.exception.CustomExceptionHandler;
import de.cogame.globalhandler.swagger.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CustomExceptionHandler.class, SwaggerConfig.class})
public class MessageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication.class, args);
    }

}
