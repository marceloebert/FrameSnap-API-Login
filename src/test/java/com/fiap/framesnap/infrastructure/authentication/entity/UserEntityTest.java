package com.fiap.framesnap.infrastructure.authentication.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void shouldCreateUserEntityWithAllFields() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@example.com";
        String passwordHash = "hashedPassword123";

        // Act
        UserEntity user = new UserEntity(id, email, passwordHash);

        // Assert
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
    }

    @Test
    void shouldCreateEmptyUserEntity() {
        // Act
        UserEntity user = new UserEntity();

        // Assert
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getEmail());
        assertNull(user.getPasswordHash());
    }

    @Test
    void shouldSetAndGetFields() {
        // Arrange
        UserEntity user = new UserEntity();
        UUID id = UUID.randomUUID();
        String email = "test@example.com";
        String passwordHash = "hashedPassword123";

        // Act
        user.setId(id);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);

        // Assert
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
    }

    @Test
    void shouldHandlePasswordAndPasswordHashEquivalently() {
        // Arrange
        UserEntity user = new UserEntity();
        String password = "password123";

        // Act
        user.setPassword(password);

        // Assert
        assertEquals(password, user.getPassword());
        assertEquals(password, user.getPasswordHash());
    }
} 