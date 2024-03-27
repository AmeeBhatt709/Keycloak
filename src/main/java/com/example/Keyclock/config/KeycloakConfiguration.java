package com.example.Keyclock.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {

    //This returns keycloak instance in this set configuration for specific client
    public Keycloak getKeycloakInstance() {
        Keycloak keycloak;
        keycloak = KeycloakBuilder
                .builder().serverUrl("http://localhost:8080").realm("demo")
                .clientId("admin-cli").grantType("password")
                .username("amee").password("amee").build();
        return keycloak;
    }


}
