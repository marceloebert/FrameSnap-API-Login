package com.fiap.framesnap.infrastructure.helthcheck.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthCheckApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnOkForHealthCheck() {
        // Act
        ResponseEntity<String> response = restTemplate.getForEntity("/health", String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }
} 