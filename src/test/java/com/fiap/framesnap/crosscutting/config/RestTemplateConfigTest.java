package com.fiap.framesnap.crosscutting.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class RestTemplateConfigTest {

    private final RestTemplateConfig config = new RestTemplateConfig();

    @Test
    void shouldCreateRestTemplateBean() {
        // Act
        RestTemplate restTemplate = config.restTemplate();

        // Assert
        assertNotNull(restTemplate);
    }
} 