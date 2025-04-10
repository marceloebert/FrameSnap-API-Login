package com.fiap.framesnap.infrastructure.authentication.controller.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationDTOsTest {

    @Test
    void shouldCreateUserRequest() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";

        // Act
        UserRequest userRequest = new UserRequest(email, password);

        // Assert
        assertNotNull(userRequest);
        assertEquals(email, userRequest.getEmail());
        assertEquals(password, userRequest.getPassword());
    }

    @Test
    void shouldCreateUserResponse() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@example.com";

        // Act
        UserResponse userResponse = new UserResponse(id, email);

        // Assert
        assertNotNull(userResponse);
        assertEquals(id, userResponse.getId());
        assertEquals(email, userResponse.getEmail());
    }

    @Test
    void shouldCreateLoginRequest() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";

        // Act
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Assert
        assertNotNull(loginRequest);
        assertEquals(email, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void shouldCreateLoginResponse() {
        // Arrange
        String token = "test-token";

        // Act
        LoginResponse loginResponse = new LoginResponse(token);

        // Assert
        assertNotNull(loginResponse);
        assertEquals(token, loginResponse.getToken());
    }

    @Test
    void shouldCreateTokenResponse() {
        // Arrange
        String token = "test-token";

        // Act
        TokenResponse tokenResponse = new TokenResponse(token);

        // Assert
        assertNotNull(tokenResponse);
        assertEquals(token, tokenResponse.getToken());
    }

    @Test
    void shouldHandleEmptyValuesInUserRequest() {
        // Arrange
        String email = "";
        String password = "";

        // Act
        UserRequest userRequest = new UserRequest(email, password);

        // Assert
        assertNotNull(userRequest);
        assertEquals(email, userRequest.getEmail());
        assertEquals(password, userRequest.getPassword());
    }

    @Test
    void shouldHandleEmptyValuesInUserResponse() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "";

        // Act
        UserResponse userResponse = new UserResponse(id, email);

        // Assert
        assertNotNull(userResponse);
        assertEquals(id, userResponse.getId());
        assertEquals(email, userResponse.getEmail());
    }

    @Test
    void shouldHandleEmptyValuesInLoginRequest() {
        // Arrange
        String email = "";
        String password = "";

        // Act
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Assert
        assertNotNull(loginRequest);
        assertEquals(email, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void shouldHandleEmptyValuesInLoginResponse() {
        // Arrange
        String token = "";

        // Act
        LoginResponse loginResponse = new LoginResponse(token);

        // Assert
        assertNotNull(loginResponse);
        assertEquals(token, loginResponse.getToken());
    }

    @Test
    void shouldHandleEmptyValuesInTokenResponse() {
        // Arrange
        String token = "";

        // Act
        TokenResponse tokenResponse = new TokenResponse(token);

        // Assert
        assertNotNull(tokenResponse);
        assertEquals(token, tokenResponse.getToken());
    }
} 