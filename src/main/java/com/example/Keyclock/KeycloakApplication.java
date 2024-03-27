package com.example.Keyclock;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@SpringBootApplication
@SecurityScheme(
		name="keycloak",
		openIdConnectUrl = "http://localhost:8080/realms/demo/.well-known/openid-configuration",
		scheme = "bearer",
		type= SecuritySchemeType.OPENIDCONNECT,
		in= SecuritySchemeIn.HEADER
)
public class KeycloakApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	public static void main(String[] args) {
		SpringApplication.run(KeycloakApplication.class, args);
	}

}
