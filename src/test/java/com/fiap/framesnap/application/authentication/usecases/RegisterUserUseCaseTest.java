package com.fiap.framesnap.application.authentication.usecases;

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

class RegisterUserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerUserUseCase = new RegisterUserUseCase(userGateway);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        UUID expectedId = UUID.randomUUID();
        
        User expectedUser = new User(expectedId, email, password);
        when(userGateway.findByEmail(email)).thenReturn(Optional.empty());
        when(userGateway.register(any(User.class))).thenReturn(expectedUser);

        // Act
        User result = registerUserUseCase.register(email, password);

        // Assert
        assertNotNull(result);
        assertEquals(expectedId, result.getId());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
        verify(userGateway, times(1)).findByEmail(email);
        verify(userGateway, times(1)).register(any(User.class));
        verifyNoMoreInteractions(userGateway);
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        // Arrange
        String email = "existing@example.com";
        String password = "password123";
        
        User existingUser = new User(UUID.randomUUID(), email, password);
        when(userGateway.findByEmail(email)).thenReturn(Optional.of(existingUser));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> registerUserUseCase.register(email, password));
        assertEquals("Usuário já existe", exception.getMessage());
        verify(userGateway, times(1)).findByEmail(email);
        verify(userGateway, never()).register(any(User.class));
        verifyNoMoreInteractions(userGateway);
    }
} 