package com.company.organization.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The NumberUtilsTest class tests the NumberUtils utility methods.
 * It leverages JUnit 5 to ensure that the utility methods function as expected.
 * These tests validate integer parsing from valid and invalid strings,
 * and verify the determination of a number's positivity.
 */
class StringUtilsTest {

    @Test
    void isEmptyWithNullTest() {
        assertTrue(StringUtils.isEmpty(null), "isEmpty should return true for null");
    }

    @Test
    void isEmptyWithEmptyStringTest() {
        assertTrue(StringUtils.isEmpty(""), "isEmpty should return true for empty string");
    }

    @Test
    void isEmptyWithBlankStringTest() {
        assertTrue(StringUtils.isEmpty("   "), "isEmpty should return true for blank string");
    }

    @Test
    void isEmptyWithNonEmptyNonBlankStringTest() {
        assertFalse(StringUtils.isEmpty("Hello"), "isEmpty should return false for non-empty, non-blank string");
    }
}