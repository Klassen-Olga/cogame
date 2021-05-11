package de.cogame.globalhandler.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

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
