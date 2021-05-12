package de.cogame.globalhandler.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Represents configuration class for common information in the SwaggerConfig class
 * binds configuration in the application.properties file with the module
 *
 * initialisation examples: swagger.title, swagger.description
 */
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    private String title;
    private String description;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


}
