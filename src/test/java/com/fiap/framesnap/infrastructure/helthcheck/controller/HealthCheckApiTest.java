package com.fiap.framesnap.infrastructure.helthcheck.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HealthCheckApiTest {

    private HealthCheckApi healthCheckApi;

    @BeforeEach
    void setUp() {
        healthCheckApi = new HealthCheckApi();
    }

    @Test
    void shouldReturnOkForHealthCheck() {
        // Act
        String response = healthCheckApi.healthCheck();

        // Assert
        assertNotNull(response);
        assertEquals("OK", response);
    }
} 