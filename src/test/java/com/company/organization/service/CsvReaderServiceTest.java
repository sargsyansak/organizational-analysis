package com.company.organization.service;

import com.company.organization.exception.InvalidCsvDataException;
import com.company.organization.exception.InvalidCsvStructureException;
import com.company.organization.service.impl.CsvReaderServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class validates the functionality of CsvReaderServiceImpl.
 * The readEmployeesDataFromCsvFileTest method checks CSV data parsing and Employee object creation.
 * The readEmployeesDataFromCsvFile_bigDataTest checks if the method can handle large data.
 * Other tests validate error handling for various incorrect CSV data formats like non-existing file, invalid file,
 * empty file, invalid managerId reference, negative salary, empty name field, CSV file with extra columns and file with negative ID.
 */
class CsvReaderServiceTest {

    @Test
    void readEmployeesDataFromCsvFileTest() {
        var csvReader = new CsvReaderServiceImpl();
        var employees = csvReader.readEmployeesDataFromCsvFile("src/test/resources/organization.csv");

        assertNotNull(employees);
        assertEquals(11, employees.size());

        var employeeWithoutManager = employees.get(120);
        assertNotNull(employeeWithoutManager);
        assertEquals(120, employeeWithoutManager.id());
        assertEquals("Mike", employeeWithoutManager.firstName());
        assertEquals("Doe", employeeWithoutManager.lastName());
        assertEquals(60000, employeeWithoutManager.salary());
        assertNull(employeeWithoutManager.managerId());
        assertNotNull(employeeWithoutManager.subordinates());
        assertEquals(1, employeeWithoutManager.subordinates().size());
        assertEquals(123, employeeWithoutManager.subordinates().get(0).id());

        var employeeWithManager = employees.get(125);
        assertNotNull(employeeWithManager);
        assertNotNull(employeeWithManager.subordinates());
        assertEquals(123, employeeWithManager.managerId());
    }

    @Test
    void readEmployeesDataFromCsvFile_bigDataTest() {
        var csvReader = new CsvReaderServiceImpl();
        var employees = csvReader.readEmployeesDataFromCsvFile("src/test/resources/bigOrganizationData.csv");

        assertNotNull(employees);
        assertEquals(1000, employees.size());
    }

    @Test
    void readEmployeesDataFromCsvFile_fileNotExists_throwExceptionTest() {
        var csvReader = new CsvReaderServiceImpl();
        assertThrows(InvalidCsvDataException.class,
                () -> csvReader.readEmployeesDataFromCsvFile("src/test/resources/invaliddd.csv"));
    }

    @Test
    void readEmployeesDataFromCsvFile_invalidFile_throwExceptionTest() {
        var csvReader = new CsvReaderServiceImpl();
        assertThrows(NumberFormatException.class,
                () -> csvReader.readEmployeesDataFromCsvFile("src/test/resources/invalid.csv"));
    }

    @Test
    void readEmployeesDataFromCsvFile_emptyFile_throwExceptionTest() {
        var csvReader = new CsvReaderServiceImpl();
        assertThrows(InvalidCsvDataException.class,
                () -> csvReader.readEmployeesDataFromCsvFile("src/test/resources/empty.csv"));
    }


    @Test
    void readEmployeesDataFromCsvFile_badNameOrLastNameField_throwExceptionTest() {
        var csvReader = new CsvReaderServiceImpl();
        assertThrows(InvalidCsvDataException.class,
                () -> csvReader.readEmployeesDataFromCsvFile("src/test/resources/organization_1.csv"));
    }


    @Test
    void readEmployeesDataFromCsvFile_fileWithExtraColumns_throwExceptionTest() {
        var csvReader = new CsvReaderServiceImpl();
        assertThrows(InvalidCsvStructureException.class,
                () -> csvReader.readEmployeesDataFromCsvFile("src/test/resources/extraColumns.csv"));
    }

    @Test
    void readEmployeesDataFromCsvFile_invalidManagerIdReference_throwExceptionTest() {
        var csvReader = new CsvReaderServiceImpl();
        assertThrows(InvalidCsvDataException.class,
                () -> csvReader.readEmployeesDataFromCsvFile("src/test/resources/managerIdReference.csv"));
    }


    @Test
    void readEmployeesDataFromCsvFile_fileWithNegativeSalary_throwExceptionTest() {
        var csvReader = new CsvReaderServiceImpl();
        assertThrows(InvalidCsvDataException.class,
                () -> csvReader.readEmployeesDataFromCsvFile("src/test/resources/negativeSalary.csv"));
    }

    @Test
    void readEmployeesDataFromCsvFile_emptyNameField_throwInvalidCsvDataExceptionTest() {
        var csvReader = new CsvReaderServiceImpl();
        assertThrows(InvalidCsvDataException.class, () -> csvReader.readEmployeesDataFromCsvFile("src/test/resources/emptyName.csv"));
    }

    @Test
    void readEmployeesDataFromCsvFileNegativeID_ThrowsInvalidCsvDataExceptionTest() {
        var csvReaderService = new CsvReaderServiceImpl();
        assertThrows(InvalidCsvDataException.class,
                () -> csvReaderService.readEmployeesDataFromCsvFile("src/test/resources/negativeID.csv"));
    }

}