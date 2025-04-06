package com.fiap.framesnap.crosscutting.validations;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class NumberValidatorTest {

    @Test
    void shouldReturnTrueForNegativeValue() {
        // Arrange
        BigDecimal value = new BigDecimal("-1");

        // Act
        boolean result = NumberValidator.isNegative(value);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForPositiveValue() {
        // Arrange
        BigDecimal value = new BigDecimal("1");

        // Act
        boolean result = NumberValidator.isNegative(value);

        // Assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForZero() {
        // Arrange
        BigDecimal value = BigDecimal.ZERO;

        // Act
        boolean result = NumberValidator.isNegative(value);

        // Assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForNull() {
        // Act
        boolean result = NumberValidator.isNegative(null);

        // Assert
        assertFalse(result);
    }
} 