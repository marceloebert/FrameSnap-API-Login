package com.fiap.framesnap.crosscutting.validations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringValidatorTest {

    @Test
    void shouldReturnTrueForNullValue() {
        // Act
        boolean result = StringValidator.isNullOrEmpty(null);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldReturnTrueForEmptyValue() {
        // Act
        boolean result = StringValidator.isNullOrEmpty("");

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldReturnTrueForBlankValue() {
        // Act
        boolean result = StringValidator.isNullOrEmpty("   ");

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForValidValue() {
        // Act
        boolean result = StringValidator.isNullOrEmpty("valid value");

        // Assert
        assertFalse(result);
    }
} 