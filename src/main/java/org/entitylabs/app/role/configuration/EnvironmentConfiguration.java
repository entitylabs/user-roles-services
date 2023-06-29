package org.entitylabs.app.role.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Configuration
public class EnvironmentConfiguration {

	@Value("${openapi.url}")
	@NotEmpty
	public List<String> openApiURL;
}
