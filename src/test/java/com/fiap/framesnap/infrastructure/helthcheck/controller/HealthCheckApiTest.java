package com.fiap.framesnap.infrastructure.helthcheck.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class HealthCheckApiTest {

    private final HealthCheckApi healthCheckApi = new HealthCheckApi();

    @Test
    void shouldReturnOkForHealthCheck() {
        // Act
        String response = healthCheckApi.healthCheck();

        // Assert
        assertEquals("OK", response);
    }
} 