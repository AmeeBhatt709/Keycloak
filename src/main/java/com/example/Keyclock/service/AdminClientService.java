package com.example.Keyclock.service;

import com.example.Keyclock.DTO.UserRequestDTO;
import com.example.Keyclock.DTO.UserResponseDTO;
import com.example.Keyclock.config.KeycloakConfiguration;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminClientService {

    private final KeycloakConfiguration keycloakConfiguration;

    private final RestTemplate restTemplate;

    public String getUser(String accessToken, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create("http://localhost:8080/admin/realms/demo/users/" + userId));

        return restTemplate.exchange(requestEntity, String.class).getBody();
    }

    public UsersResource getUserResource() {
        Keycloak keycloak = keycloakConfiguration.getKeycloakInstance();
        return keycloak.realm("demo").users();
    }

    public UserRepresentation setUser(UserRequestDTO userRequest) {

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userRequest.getPassword());
        user.setCredentials(List.of(credential));
        return user;
    }

    public List<UserResponseDTO> getAllUser() {
        List<UserRepresentation> userRepresentations =
                keycloakConfiguration.getKeycloakInstance().realm("demo").users().list();
        return userRepresentations.stream().map(this::setUserResponse).collect(Collectors.toList());
    }

    private UserResponseDTO setUserResponse(UserRepresentation userRepresentation) {
        return UserResponseDTO.builder()
                .id(userRepresentation.getId())
                .userName(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .build();
    }

    public UserResponseDTO getUserByUserName(String userName) {
        return this.getUserResource()
                .search(userName)
                .stream()
                .findFirst()
                .map(this::setUserResponse)
                .orElse(new UserResponseDTO());

    }
}
