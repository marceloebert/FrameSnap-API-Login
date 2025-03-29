package com.fiap.framesnap.infrastructure.authentication.controller;

import com.fiap.framesnap.application.authentication.usecases.LoginUseCase;
import com.fiap.framesnap.application.authentication.usecases.RegisterUserUseCase;
import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserRequest;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserResponse;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.LoginRequest;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void shouldRegisterUserSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        UUID userId = UUID.randomUUID();
        
        UserRequest request = new UserRequest(email, password);
        User user = new User(userId, email, password);
        
        when(registerUserUseCase.register(email, password)).thenReturn(user);

        // Act
        ResponseEntity<UserResponse> response = authApi.registerUser(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserResponse userResponse = response.getBody();
        assertNotNull(userResponse);
        assertEquals(userId, userResponse.getId());
        assertEquals(email, userResponse.getEmail());
        verify(registerUserUseCase).register(email, password);
    }

    @Test
    void shouldLoginSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String expectedToken = "jwt-token";
        
        LoginRequest request = new LoginRequest(email, password);
        when(loginUseCase.execute(email, password)).thenReturn(expectedToken);

        // Act
        LoginResponse response = authApi.login(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedToken, response.getToken());
        verify(loginUseCase).execute(email, password);
    }
} 