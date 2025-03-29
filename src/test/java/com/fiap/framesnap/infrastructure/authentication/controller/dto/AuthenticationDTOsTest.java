package com.fiap.framesnap.infrastructure.authentication.controller.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationDTOsTest {

    @Test
    void shouldCreateAndTestUserRequest() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";

        // Act
        UserRequest request = new UserRequest(email, password);

        // Assert
        assertNotNull(request);
        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());

        // Test empty constructor
        UserRequest emptyRequest = new UserRequest();
        assertNotNull(emptyRequest);
        assertNull(emptyRequest.getEmail());
        assertNull(emptyRequest.getPassword());
    }

    @Test
    void shouldCreateAndTestUserResponse() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@example.com";

        // Act
        UserResponse response = new UserResponse(id, email);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(email, response.getEmail());

        // Test setters
        UUID newId = UUID.randomUUID();
        String newEmail = "new@example.com";
        response.setId(newId);
        response.setEmail(newEmail);
        assertEquals(newId, response.getId());
        assertEquals(newEmail, response.getEmail());

        // Test empty constructor
        UserResponse emptyResponse = new UserResponse();
        assertNotNull(emptyResponse);
        assertNull(emptyResponse.getId());
        assertNull(emptyResponse.getEmail());
    }

    @Test
    void shouldCreateAndTestLoginRequest() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";

        // Act
        LoginRequest request = new LoginRequest(email, password);

        // Assert
        assertNotNull(request);
        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());

        // Test setters
        String newEmail = "new@example.com";
        String newPassword = "newPassword123";
        request.setEmail(newEmail);
        request.setPassword(newPassword);
        assertEquals(newEmail, request.getEmail());
        assertEquals(newPassword, request.getPassword());

        // Test empty constructor
        LoginRequest emptyRequest = new LoginRequest();
        assertNotNull(emptyRequest);
        assertNull(emptyRequest.getEmail());
        assertNull(emptyRequest.getPassword());
    }

    @Test
    void shouldCreateAndTestLoginResponse() {
        // Arrange
        String token = "jwt-token";

        // Act
        LoginResponse response = new LoginResponse(token);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.getToken());

        // Test setters
        String newToken = "new-jwt-token";
        response.setToken(newToken);
        assertEquals(newToken, response.getToken());

        // Test empty constructor
        LoginResponse emptyResponse = new LoginResponse();
        assertNotNull(emptyResponse);
        assertNull(emptyResponse.getToken());
    }

    @Test
    void shouldCreateAndTestTokenResponse() {
        // Arrange
        String token = "jwt-token";

        // Act
        TokenResponse response = new TokenResponse(token);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.getToken());
    }
} 