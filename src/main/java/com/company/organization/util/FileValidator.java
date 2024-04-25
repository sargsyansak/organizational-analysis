package com.company.organization.util;

import com.company.organization.exception.InvalidCsvDataException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileValidator {

    private static final Logger logger = Logger.getLogger(FileValidator.class.getName());

    private FileValidator() {
        throw new UnsupportedOperationException("Cannot instantiate utility class.");
    }

    public static void validateFile(String filePath) {
        validateFileExists(filePath);
        validateNotEmpty(filePath);
        validateFileReadable(filePath);
        validateCsvFileExtension(filePath);
    }

    private static void validateFileExists(String filePath) {
        if (!Files.exists(Path.of(filePath))) {
            String message = String.format("File not found: %s", filePath);
            logger.log(Level.SEVERE, message);
            throw new InvalidCsvDataException(message);
        }
    }

    private static void validateFileReadable(String filePath) {
        if (!Files.isReadable(Path.of(filePath))) {
            String message = String.format("File is not readable or accessible: %s", filePath);
            logger.log(Level.SEVERE, message);
            throw new InvalidCsvDataException(message);
        }
    }

    private static void validateCsvFileExtension(String filePath) {
        if (!filePath.toLowerCase().endsWith(".csv")) {
            String message = String.format("Invalid file extension. Expected .csv file: %s", filePath);
            logger.log(Level.SEVERE, message);
            throw new InvalidCsvDataException(message);
        }
    }

    public static void validateNotEmpty(String filePath) {
        Path path = Paths.get(filePath);
        try {
            if (Files.size(path) == 0) {
                String message = "CSV file does not contain any data.";
                logger.warning(message);
                throw new InvalidCsvDataException(message);
            }
        } catch (IOException e) {
            String message = "Error occurred while processing data.";
            logger.warning(message);
            throw new InvalidCsvDataException(message);
        }
    }
}
