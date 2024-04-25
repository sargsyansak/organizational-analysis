package com.company.organization.util;

import java.util.Objects;

public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("Can not instantiate utility class");
    }

    public static boolean isEmpty(String value) {
        return Objects.isNull(value) || value.isBlank();
    }
}