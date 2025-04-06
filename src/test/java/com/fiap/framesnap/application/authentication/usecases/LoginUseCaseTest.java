package com.fiap.framesnap.application.authentication.usecases;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import com.fiap.framesnap.application.authentication.gateways.UserGateway;
import com.fiap.framesnap.entities.authentication.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private TokenGateway tokenGateway;

    private LoginUseCase loginUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginUseCase = new LoginUseCase(userGateway, tokenGateway);
    }

    @Test
    void shouldLoginSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String expectedToken = "jwt-token";
        
        User user = new User(UUID.randomUUID(), email, password);
        when(userGateway.findByEmail(email)).thenReturn(Optional.of(user));
        when(tokenGateway.generateToken(email, password)).thenReturn(expectedToken);

        // Act
        String result = loginUseCase.execute(email, password);

        // Assert
        assertNotNull(result);
        assertEquals(expectedToken, result);
        verify(userGateway, times(1)).findByEmail(email);
        verify(tokenGateway, times(1)).generateToken(email, password);
        verifyNoMoreInteractions(userGateway, tokenGateway);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password123";
        
        when(userGateway.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            loginUseCase.execute(email, password);
        });
        verify(userGateway, times(1)).findByEmail(email);
        verify(tokenGateway, never()).generateToken(any(), any());
    }
} 