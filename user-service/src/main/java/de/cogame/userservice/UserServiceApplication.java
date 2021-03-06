package de.cogame.userservice;


import de.cogame.globalhandler.exception.CustomExceptionHandler;
import de.cogame.globalhandler.security.SecurityConfiguration;
import de.cogame.globalhandler.swagger.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
@EnableFeignClients
@SpringBootApplication
@Import({CustomExceptionHandler.class, SecurityConfiguration.class, SwaggerConfig.class})
public class UserServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
