package com.fiap.framesnap.crosscutting.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest2 {

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