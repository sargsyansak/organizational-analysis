package com.company.organization.service.impl;

import com.company.organization.data.Employee;
import com.company.organization.exception.CsvIOException;
import com.company.organization.exception.InvalidCsvDataException;
import com.company.organization.exception.InvalidCsvStructureException;
import com.company.organization.service.CsvReaderService;
import com.company.organization.util.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.company.organization.util.FileValidator.validateFile;
import static com.company.organization.util.StringUtils.isEmpty;

public class CsvReaderServiceImpl implements CsvReaderService {

    private static final Logger logger = Logger.getLogger(CsvReaderServiceImpl.class.getName());

    private static final int ID_INDEX = 0;
    private static final int FIRST_NAME_INDEX = 1;
    private static final int LAST_NAME_INDEX = 2;
    private static final int SALARY_INDEX = 3;
    private static final int MANAGER_ID_INDEX = 4;

    private static final int MIN_EXPECTED_ROW_LENGTH = 4;
    private static final int MAX_EXPECTED_ROW_LENGTH = 5;

    @Override
    public Map<Integer, Employee> readEmployeesDataFromCsvFile(String csvFile) {

        validateFile(csvFile);
        List<String> lines = readAllLines(csvFile);
        Map<Integer, Employee> employees = mapToEmployees(lines);
        checkDuplicateIds(employees);
        establishEmployeeManagementHierarchy(employees);
        return employees;


    }

    private List<String> readAllLines(String csvFile) {
        try {
            return Files.readAllLines(Path.of(csvFile));
        } catch (IOException e) {
            String errorMessage = "Error reading CSV file: " + e.getMessage();
            logger.log(Level.SEVERE, e, () -> errorMessage);
            throw new CsvIOException(errorMessage, e);
        }
    }

    private Map<Integer, Employee> mapToEmployees(List<String> lines) {
        return lines.stream().map(line -> line.split(","))
                .skip(1) // skipping header
                .collect(Collectors.toMap(row -> Integer.parseInt(row[ID_INDEX]),
                        this::createEmployeeFromCsvRow));
    }

    private void validateRow(String[] row) {
        if (row.length > MAX_EXPECTED_ROW_LENGTH || row.length < MIN_EXPECTED_ROW_LENGTH) {
            String errorMessage = "Error reading CSV file as it has an invalid format and structure.";
            logger.warning(errorMessage);
            throw new InvalidCsvStructureException(errorMessage);
        }

        String firstName = row[FIRST_NAME_INDEX];
        String lastName = row[LAST_NAME_INDEX];
        if (isEmpty(firstName) || isEmpty(lastName)) {
            String message = String.format("Empty fields: firstName=%s, lastName=%s", firstName, lastName);
            logger.warning(message);
            throw new InvalidCsvDataException(message);
        }

        int id = NumberUtils.parseInt(row[ID_INDEX].trim());
        if (!NumberUtils.isPositive(id)) {
            String message = String.format("Invalid ID or salary value: ID=%d", id);
            logger.warning(message);
            throw new InvalidCsvDataException(message);
        }

        int salary = NumberUtils.parseInt(row[SALARY_INDEX].trim());
        if (!NumberUtils.isPositive(salary)) {
            String message = String.format("Invalid ID or salary value: salary=%d", salary);
            logger.warning(message);
            throw new InvalidCsvDataException(message);
        }
    }

    private void establishEmployeeManagementHierarchy(Map<Integer, Employee> employees) {
        employees.values().stream().filter(employee -> employee.managerId() != null)
                .forEach(employee -> {
                    Employee manager = employees.get(employee.managerId());
                    if (manager != null) {
                        manager.subordinates().add(employee);
                    }
                });
    }

    private Employee createEmployeeFromCsvRow(String[] row) {
        validateRow(row);
        return new Employee(
                Integer.parseInt(row[ID_INDEX]),
                row[FIRST_NAME_INDEX].trim(),
                row[LAST_NAME_INDEX].trim(),
                Integer.parseInt(row[SALARY_INDEX]),
                row.length > MIN_EXPECTED_ROW_LENGTH ? Integer.parseInt(row[MANAGER_ID_INDEX].trim()) : null
        );
    }

    private static void checkDuplicateIds(Map<Integer, Employee> employees) {
        Map<Integer, Employee> idMap = new HashMap<>();
        employees.values()
                .forEach(employee -> {
                    Integer id = employee.id();
                    if (idMap.containsKey(id)) {
                        String message = String.format("Duplicate ID found in CSV file: %d", id);
                        logger.severe(message);
                        throw new InvalidCsvDataException(message);
                    }
                    idMap.put(id, employee);
                });
    }

}