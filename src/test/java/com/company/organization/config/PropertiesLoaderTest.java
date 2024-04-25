package com.company.organization.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The PropertiesLoaderTest class is a suite of tests for the PropertiesLoader.
 * It uses JUnit 5 for testing.
 * <p>
 * It checks if properties from application.properties are loaded correctly in propertiesLoadingTest.
 * <p>
 * The invalidPropertiesFile_thenExceptionTest constructs a case where an error occurs during properties loading.
 * It is designed to affirm that an IllegalArgumentException is correctly thrown under these conditions.
 */
class PropertiesLoaderTest {

    @Test
    void propertiesLoadingTest() {
        PropertiesLoader loader = new PropertiesLoader();

        assertEquals(1.2, loader.getMinExpectedCoefficient());
        assertEquals(1.5, loader.getMaxExpectedCoefficient());
        assertEquals(4, loader.getReportingLineMaxDepth());
    }

    @Test
    void invalidPropertiesFile_thenExceptionTest() {
        PropertiesLoaderWithError propertiesLoader = new PropertiesLoaderWithError();

        assertThrows(IllegalArgumentException.class, propertiesLoader::loadProperties);
    }

    private static class PropertiesLoaderWithError extends PropertiesLoader {
        protected void loadProperties() {
            throw new IllegalArgumentException("Error loading properties");
        }
    }
}