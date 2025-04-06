package com.fiap.framesnap.application.authentication.entities;

import com.fiap.framesnap.entities.authentication.User;
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