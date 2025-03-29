package com.fiap.framesnap.crosscutting.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    private final AppConfig appConfig = new AppConfig();

    @Test
    void shouldCreateAppConfigInstance() {
        // Assert
        assertNotNull(appConfig);
    }
} 