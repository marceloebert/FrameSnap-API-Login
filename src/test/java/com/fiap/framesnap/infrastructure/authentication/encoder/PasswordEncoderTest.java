package com.fiap.framesnap.crosscutting.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEncoder();
    }

    @Test
    void shouldEncodePassword() {
        // Arrange
        String password = "password123";

        // Act
        String encodedPassword = passwordEncoder.encode(password);

        // Assert
        assertNotNull(encodedPassword);
        assertTrue(encodedPassword.length() > 0);
        assertNotEquals(password, encodedPassword);
    }

    @Test
    void shouldGenerateDifferentHashesForSamePassword() {
        // Arrange
        String password = "password123";

        // Act
        String hash1 = passwordEncoder.encode(password);
        String hash2 = passwordEncoder.encode(password);

        // Assert
        assertNotNull(hash1);
        assertNotNull(hash2);
        assertNotEquals(hash1, hash2);
    }

    @Test
    void shouldVerifyPassword() {
        // Arrange
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        // Act
        boolean isValid = passwordEncoder.matches(password, encodedPassword);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseForInvalidPassword() {
        // Arrange
        String password = "password123";
        String wrongPassword = "wrongpassword";
        String encodedPassword = passwordEncoder.encode(password);

        // Act
        boolean isValid = passwordEncoder.matches(wrongPassword, encodedPassword);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void shouldHandleNullPassword() {
        // Act
        String encodedPassword = passwordEncoder.encode(null);

        // Assert
        assertNull(encodedPassword);
    }

    @Test
    void shouldHandleEmptyPassword() {
        // Arrange
        String password = "";

        // Act
        String encodedPassword = passwordEncoder.encode(password);

        // Assert
        assertNotNull(encodedPassword);
        assertTrue(encodedPassword.length() > 0);
    }

    @Test
    void shouldHandleNullPasswordForVerification() {
        // Arrange
        String encodedPassword = passwordEncoder.encode("password123");

        // Act
        boolean isValid = passwordEncoder.matches(null, encodedPassword);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void shouldHandleNullEncodedPasswordForVerification() {
        // Arrange
        String password = "password123";

        // Act
        boolean isValid = passwordEncoder.matches(password, null);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void shouldHandleEmptyPasswordForVerification() {
        // Arrange
        String password = "";
        String encodedPassword = passwordEncoder.encode(password);

        // Act
        boolean isValid = passwordEncoder.matches(password, encodedPassword);

        // Assert
        assertTrue(isValid);
    }
} 