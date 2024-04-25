package com.company.organization.util;

import com.company.organization.exception.InvalidCsvDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The NumberUtilsTest class tests the NumberUtils utility methods.
 * It leverages JUnit 5 to ensure that the utility methods function as expected.
 * These tests validate integer parsing from valid and invalid strings,
 * and verify the determination of a number's positivity.
 */
class NumberUtilsTest {

    @Test
    void parseIntValidTest() {
        String validInt = " 123 ";
        int parsedInt = NumberUtils.parseInt(validInt);
        assertEquals(123, parsedInt, "Parsed integer is incorrect");
    }

    @Test
    void parseIntInvalidTest() {
        String invalidInt = "one two three";
        assertThrows(InvalidCsvDataException.class, () -> NumberUtils.parseInt(invalidInt), "Should throw InvalidCsvDataException");
    }

    @Test
    void isPositiveTrueTest() {
        assertTrue(NumberUtils.isPositive(1), "Should return true for positive numbers");
    }

    @Test
    void issPositiveFalseTest() {
        assertFalse(NumberUtils.isPositive(-1), "Should return false for negative numbers");
        assertFalse(NumberUtils.isPositive(0), "Should return false for zero");
    }
}