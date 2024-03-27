package com.example.Keyclock.controller;

import com.example.Keyclock.DTO.UserRequestDTO;
import com.example.Keyclock.DTO.UserResponseDTO;
import com.example.Keyclock.config.KeycloakConfiguration;
import com.example.Keyclock.service.AdminClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/demo")
@SecurityRequirement(name = "keycloak")
public class HelloController {

    @Autowired
    private KeycloakConfiguration keycloakConfiguration;

    @Autowired
    private AdminClientService adminClientService;


    @GetMapping
    @PreAuthorize("hasRole('client_user')")
    public String hello() {
        return "Hello from Spring boot & Keycloak";
    }

    @GetMapping("/hello-2")
    @PreAuthorize("hasRole('client_user')")
    public String hello2() {
        return "Hello from Spring boot & Keycloak - ADMIN";
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers() {
        return this.adminClientService.getAllUser();
    }

    @GetMapping("/users/{userId}")
    public String getUser(HttpServletRequest request, @PathVariable(name = "userId") String userId) {
        String token = request.getHeader("Authorization").substring(7, request.getHeader("Authorization").length());
        return adminClientService.getUser(token, userId);
    }

    @GetMapping("/usersByName")
    public UserResponseDTO getUserByUserName(String userName) {
        return this.adminClientService.getUserByUserName(userName);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDTO userRequest) {
        UsersResource usersResource = this.adminClientService.getUserResource();
        usersResource.create(this.adminClientService.setUser(userRequest));
        return ResponseEntity.ok("User registered successfully");
    }

}