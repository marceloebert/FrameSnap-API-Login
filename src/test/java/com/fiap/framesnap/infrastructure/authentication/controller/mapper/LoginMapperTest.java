package com.fiap.framesnap.infrastructure.authentication.controller.mapper;

import com.fiap.framesnap.infrastructure.authentication.controller.dto.LoginResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginMapperTest {

    @Test
    void shouldMapTokenToLoginResponse() {
        // Arrange
        String token = "jwt-token";

        // Act
        LoginResponse response = LoginMapper.toResponse(token);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.getToken());
    }

    @Test
    void shouldHandleNullToken() {
        // Act
        LoginResponse response = LoginMapper.toResponse(null);

        // Assert
        assertNull(response);
    }

    @Test
    void shouldHandleEmptyToken() {
        // Arrange
        String token = "";

        // Act
        LoginResponse response = LoginMapper.toResponse(token);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.getToken());
    }
} 