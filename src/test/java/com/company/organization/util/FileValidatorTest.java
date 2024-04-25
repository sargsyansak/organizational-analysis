package com.company.organization.util;

import com.company.organization.exception.InvalidCsvDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The FileValidatorTest class tests the FileValidator utilities.
 * It uses JUnit 5 and @TempDir for temporary directory handling.
 * It tests file validations like checking existence, readability, and correct file extensions.
 * Be aware, file access tests may fail on systems with restricted permissions.
 */
class FileValidatorTest {

    @TempDir
    Path tempDir;

    @Test
    void validateFileExistsTest() {
        String nonExistentPath = "nonexistent.txt";
        assertThrows(InvalidCsvDataException.class, () -> FileValidator.validateFile(nonExistentPath));
    }

    @Test
    void validateFileReadableTest() throws IOException {
        File unreadableFile = new File(tempDir.resolve("unreadable.txt").toString());
        unreadableFile.createNewFile();
        unreadableFile.setReadable(false);
        assertThrows(InvalidCsvDataException.class, () -> FileValidator.validateFile(unreadableFile.getPath()));
    }

    @Test
    void validateCsvFileExtensionTest() {
        String invalidExtensionFilePath = tempDir.resolve("invalidExtension.txt").toString();
        assertThrows(InvalidCsvDataException.class, () -> FileValidator.validateFile(invalidExtensionFilePath));
    }

    @Test
    void validateNotEmptyTest() throws IOException {
        String emptyFilePath = tempDir.resolve("empty.csv").toString();
        Files.createFile(Paths.get(emptyFilePath));
        assertThrows(InvalidCsvDataException.class, () -> FileValidator.validateFile(emptyFilePath));
    }

    @Test
    void validateFileTest() throws IOException {
        String validFilePath = tempDir.resolve("valid.csv").toString();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(validFilePath))) {
            writer.write("valid content");
        }
        assertDoesNotThrow(() -> FileValidator.validateFile(validFilePath));
    }

    @Test
    void validateFile_nonexistentFile_throwsInvalidCsvDataExceptionTest() {
        String nonExistentFilePath = tempDir.resolve("nonexistent.csv").toString();
        assertThrows(InvalidCsvDataException.class, () -> FileValidator.validateFile(nonExistentFilePath));
    }

}