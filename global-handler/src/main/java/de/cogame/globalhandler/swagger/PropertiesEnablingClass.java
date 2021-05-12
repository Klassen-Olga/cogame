package de.cogame.globalhandler.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/*
*  Activates SwaggerProperties class to be used between SwaggerConfig class and other modules
*
* */
@Service
@EnableConfigurationProperties(SwaggerProperties.class)
public class PropertiesEnablingClass {

	private final SwaggerProperties swaggerProperties;

	public PropertiesEnablingClass(SwaggerProperties serviceProperties) {
		this.swaggerProperties = serviceProperties;
	}

	public String message() {
		return this.swaggerProperties.getTitle();
	}
}
