package com.company.organization.config;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesLoader {
    private static final Logger logger = Logger.getLogger(PropertiesLoader.class.getName());

    private double minExpectedCoefficient;
    private double maxExpectedCoefficient;
    private int reportingLineMaxDepth;

    public PropertiesLoader() {
        loadProperties();
    }

    public double getMinExpectedCoefficient() {
        return minExpectedCoefficient;
    }

    public double getMaxExpectedCoefficient() {
        return maxExpectedCoefficient;
    }

    public int getReportingLineMaxDepth() {
        return reportingLineMaxDepth;
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {

            properties.load(inputStream);
            minExpectedCoefficient = Double.parseDouble(properties.getProperty("min.expected.coefficient", "1.2"));
            maxExpectedCoefficient = Double.parseDouble(properties.getProperty("max.expected.coefficient", "1.5"));
            reportingLineMaxDepth = Integer.parseInt(properties.getProperty("reporting.line.max.depth", "4"));

        } catch (Exception e) {
            logger.severe("Error loading application.properties: " + e.getMessage());
            throw new IllegalArgumentException();
        }
    }
}
