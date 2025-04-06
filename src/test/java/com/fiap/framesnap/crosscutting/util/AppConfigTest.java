package com.fiap.framesnap.crosscutting.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppConfigTest {

    private final AppConfig appConfig = new AppConfig();

    @Test
    void shouldCreateAppConfig() {
        // Assert
        assertNotNull(appConfig);
    }
} 