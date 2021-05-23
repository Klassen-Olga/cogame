package de.cogame.eventservice;

import de.cogame.globalhandler.exception.CustomExceptionHandler;
import de.cogame.globalhandler.swagger.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableFeignClients
@Import({CustomExceptionHandler.class, SwaggerConfig.class})
@SpringBootApplication
public class EventServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }

}
