package com.fiap.framesnap.crosscutting.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HashUtilTest {

    @Test
    void shouldCalculateSecretHash() {
        // Arrange
        String clientId = "test-client-id";
        String clientSecret = "test-client-secret";
        String username = "test-username";

        // Act
        String hash = HashUtil.calculateSecretHash(clientId, clientSecret, username);

        // Assert
        assertNotNull(hash);
        assertTrue(hash.length() > 0);
    }

    @Test
    void shouldCalculateDifferentHashesForDifferentInputs() {
        // Arrange
        String clientId = "test-client-id";
        String clientSecret = "test-client-secret";
        String username1 = "test-username-1";
        String username2 = "test-username-2";

        // Act
        String hash1 = HashUtil.calculateSecretHash(clientId, clientSecret, username1);
        String hash2 = HashUtil.calculateSecretHash(clientId, clientSecret, username2);

        // Assert
        assertNotNull(hash1);
        assertNotNull(hash2);
        assertNotEquals(hash1, hash2);
    }

    @Test
    void shouldThrowExceptionForNullInputs() {
        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            HashUtil.calculateSecretHash(null, null, null);
        });
    }

    @Test
    void shouldThrowExceptionForEmptyInputs() {
        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            HashUtil.calculateSecretHash("", "", "");
        });
    }
} 