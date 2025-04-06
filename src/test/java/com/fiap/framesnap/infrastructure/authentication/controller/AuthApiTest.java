package com.fiap.framesnap.infrastructure.authentication.controller;

import com.fiap.framesnap.application.authentication.usecases.LoginUseCase;
import com.fiap.framesnap.application.authentication.usecases.RegisterUserUseCase;
import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.LoginRequest;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.LoginResponse;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserRequest;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthApiTest {

    @Mock
    private LoginUseCase loginUseCase;

    @Mock
    private RegisterUserUseCase registerUserUseCase;

    private AuthApi authApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authApi = new AuthApi(loginUseCase, registerUserUseCase);
    }

    @Test
    void shouldRegisterUser() {
        // Arrange
        UserRequest userRequest = new UserRequest("test@example.com", "password123");
        UUID id = UUID.randomUUID();
        User user = new User(id, userRequest.getEmail(), userRequest.getPassword());

        when(registerUserUseCase.register(any(), any())).thenReturn(user);

        // Act
        ResponseEntity<UserResponse> response = authApi.registerUser(userRequest);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(user.getEmail(), response.getBody().getEmail());
    }

    @Test
    void shouldReturnBadRequestForInvalidRegistration() {
        // Arrange
        UserRequest userRequest = new UserRequest("test@example.com", "password123");
        when(registerUserUseCase.register(any(), any()))
                .thenThrow(new RuntimeException("Usuário já existe"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authApi.registerUser(userRequest);
        });
    }

    @Test
    void shouldLoginUser() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");
        String expectedToken = "test-token";

        when(loginUseCase.execute(any(), any())).thenReturn(expectedToken);

        // Act
        LoginResponse response = authApi.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(expectedToken, response.getToken());
    }

    @Test
    void shouldReturnUnauthorizedForInvalidLogin() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("test@example.com", "wrong-password");
        when(loginUseCase.execute(any(), any()))
                .thenThrow(new RuntimeException("Usuário não encontrado"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authApi.login(loginRequest);
        });
    }
} 