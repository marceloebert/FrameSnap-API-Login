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
    void shouldHandleNullValues() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            new User(id, null, null);
        });
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