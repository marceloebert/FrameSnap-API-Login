package com.fiap.framesnap.entities.authentication;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserWithAllFields() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@example.com";
        String password = "password123";

        // Act
        User user = new User(id, email, password);

        // Assert
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    void shouldHandleNullEmail() {
        // Arrange
        UUID id = UUID.randomUUID();
        String password = "password123";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new User(id, null, password);
        });

        assertEquals("Email não pode ser nulo", exception.getMessage());
    }

    @Test
    void shouldHandleNullPassword() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@example.com";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new User(id, email, null);
        });

        assertEquals("Senha não pode ser nula", exception.getMessage());
    }

    @Test
    void shouldHandleEmptyValues() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "";
        String password = "";

        // Act
        User user = new User(id, email, password);

        // Assert
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }
} 