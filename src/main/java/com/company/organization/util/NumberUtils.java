package com.company.organization.util;

import com.company.organization.exception.InvalidCsvDataException;

import java.util.logging.Logger;

public class NumberUtils {
    private static final Logger logger = Logger.getLogger(NumberUtils.class.getName());

    private NumberUtils() {
        throw new UnsupportedOperationException("Can not instantiate utility class.");
    }

    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            logger.severe("Error parsing integer data in CSV");
            throw new InvalidCsvDataException("Error parsing integer data in CSV");
        }
    }

    public static boolean isPositive(int value) {
        return value > 0;
    }
}
