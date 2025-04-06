package com.fiap.framesnap.infrastructure.authentication.entity;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void shouldCreateUserEntity() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@example.com";
        String password = "password123";

        // Act
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setEmail(email);
        userEntity.setPassword(password);

        // Assert
        assertNotNull(userEntity);
        assertEquals(id, userEntity.getId());
        assertEquals(email, userEntity.getEmail());
        assertEquals(password, userEntity.getPassword());
    }

    @Test
    void shouldCreateUserEntityWithConstructor() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@example.com";
        String password = "password123";

        // Act
        UserEntity userEntity = new UserEntity(id, email, password);

        // Assert
        assertNotNull(userEntity);
        assertEquals(id, userEntity.getId());
        assertEquals(email, userEntity.getEmail());
        assertEquals(password, userEntity.getPassword());
    }

    @Test
    void shouldSetAndGetId() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        UUID id = UUID.randomUUID();

        // Act
        userEntity.setId(id);

        // Assert
        assertEquals(id, userEntity.getId());
    }

    @Test
    void shouldSetAndGetEmail() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        String email = "test@example.com";

        // Act
        userEntity.setEmail(email);

        // Assert
        assertEquals(email, userEntity.getEmail());
    }

    @Test
    void shouldSetAndGetPassword() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        String password = "password123";

        // Act
        userEntity.setPassword(password);

        // Assert
        assertEquals(password, userEntity.getPassword());
    }

    @Test
    void shouldHandleNullValues() {
        // Act
        UserEntity userEntity = new UserEntity();

        // Assert
        assertNull(userEntity.getId());
        assertNull(userEntity.getEmail());
        assertNull(userEntity.getPassword());
    }

    @Test
    void shouldHandleEmptyValues() {
        // Arrange
        UserEntity userEntity = new UserEntity();

        // Act
        userEntity.setEmail("");
        userEntity.setPassword("");

        // Assert
        assertNull(userEntity.getId());
        assertEquals("", userEntity.getEmail());
        assertEquals("", userEntity.getPassword());
    }

    @Test
    void shouldHandleNullId() {
        // Arrange
        UserEntity userEntity = new UserEntity();

        // Act
        userEntity.setId(null);

        // Assert
        assertNull(userEntity.getId());
    }

    @Test
    void shouldHandleNullEmail() {
        // Arrange
        UserEntity userEntity = new UserEntity();

        // Act
        userEntity.setEmail(null);

        // Assert
        assertNull(userEntity.getEmail());
    }

    @Test
    void shouldHandleNullPassword() {
        // Arrange
        UserEntity userEntity = new UserEntity();

        // Act
        userEntity.setPassword(null);

        // Assert
        assertNull(userEntity.getPassword());
    }
} 