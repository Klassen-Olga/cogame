package de.cogame.globalhandler.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// URL: http://localhost:8001/v2/api-docs
//      http://localhost:8001/swagger-ui/index.html
@Configuration
@EnableSwagger2
//TODO: add authentification details
public class SwaggerConfig {

    @Value("${swagger.title}")
    public String title;
    @Value("${swagger.description}")
    public String description;



    private static final Contact DEFAULT_CONTACT = new Contact("Klassen Olga",
            "", "klassen.olga@fh-erfurt.de");

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(Arrays.asList("application/json"));

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfo(
                        title,
                        description,
                        "1.0.0",
                        "Terms of service",
                        DEFAULT_CONTACT,
                        "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Arrays.asList()))
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);


    }


}
