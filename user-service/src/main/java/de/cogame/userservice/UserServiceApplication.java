package de.cogame.userservice;




import de.cogame.globalhandler.exception.CustomExceptionHandler;
import de.cogame.globalhandler.security.SecurityConfiguration;
import de.cogame.globalhandler.swagger.SwaggerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Import({CustomExceptionHandler.class, SecurityConfiguration.class, SwaggerConfig.class})
public class UserServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
