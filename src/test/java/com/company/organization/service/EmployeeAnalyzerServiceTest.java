package com.company.organization.service;

import com.company.organization.data.Employee;
import com.company.organization.service.impl.EmployeeAnalyzerServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class validates the functionality of CsvReaderServiceImpl.
 * The readEmployeesDataFromCsvFileTest method checks CSV data parsing and Employee object creation.
 * The readEmployeesDataFromCsvFile_bigDataTest checks if the method can handle large data.
 * Other tests validate error handling for various incorrect CSV data formats like non-existing file, invalid file,
 * empty file, invalid managerId reference, negative salary, empty name field, CSV file with extra columns and file with negative ID.
 */
class EmployeeAnalyzerServiceTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Map<Integer, Employee> employees;
    private final PrintStream originalOut = System.out;


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void processAnalyze_singleEmployeeWithNoSubordinatesTest() {
        Employee ceo = new Employee(1, "John", "Doe", 5000, null);
        Map<Integer, Employee> employees = Collections.singletonMap(1, ceo);
        EmployeeAnalyzerServiceImpl analyzer = new EmployeeAnalyzerServiceImpl();

        analyzer.processAnalyze(employees);
        assertTrue(outputStreamCaptor.toString().trim().contains("There are no employees in this category. All good!!!"));
    }

    @Test
    void processAnalyze_singleEmployeeWithSubordinatesTest() {

        Employee ceo = new Employee(1, "John", "Doe", 5000, null);
        Employee subordinate1 = new Employee(2, "Jane", "Doe", 4000, 1);
        Employee subordinate2 = new Employee(3, "Alice", "Smith", 6000, 1);

        Map<Integer, Employee> employees = new HashMap<>();
        employees.put(1, ceo);
        employees.put(2, subordinate1);
        employees.put(3, subordinate2);

        EmployeeAnalyzerServiceImpl analyzer = new EmployeeAnalyzerServiceImpl();

        analyzer.processAnalyze(employees);
        assertTrue(outputStreamCaptor.toString().trim().contains("There are no employees in this category. All good!!!"));

    }

    @Test
    void processAnalyze_employeeSalariesTest() {
        employees = getEmployeesMap();
        establishEmployeeManagementHierarchy();

        EmployeeAnalyzerService companyEmployeeAnalyzer = new EmployeeAnalyzerServiceImpl();
        companyEmployeeAnalyzer.processAnalyze(employees);

        String expected = """
                ************************
                Employees that earns less than expected
                Employee id=124, Martin Chekov earns less than expected by 45000.0.
                Employee id=309, John Smith earns less than expected by 20000.0.
                Employee id=310, Anna Smith earns less than expected by 20000.0.
                ************************

                ************************
                Employees that earns more than expected
                Employee id=311, Anthony Brown earns more than expected by 25000.0.
                ************************

                ************************
                Employees that exceeds max allowed reporting line.
                There are no employees in this category. All good!!!
                ************************""";

        String actual = outputStreamCaptor.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }


    @Test
    void processAnalyze_reportingLinesTest() {
        getEmployeesMap().put(313, new Employee(313, "Brad", "Smith", 37000, 312,
                new ArrayList<>()));

        establishEmployeeManagementHierarchy();

        EmployeeAnalyzerService companyEmployeeAnalyzer = new EmployeeAnalyzerServiceImpl();
        companyEmployeeAnalyzer.processAnalyze(employees);

        String expected = """
                ************************
                Employees that earns less than expected
                Employee id=124, Martin Chekov earns less than expected by 45000.0.
                Employee id=309, John Smith earns less than expected by 20000.0.
                Employee id=310, Anna Smith earns less than expected by 20000.0.
                ************************

                ************************
                Employees that earns more than expected
                Employee id=311, Anthony Brown earns more than expected by 25000.0.
                ************************

                ************************
                Employees that exceeds max allowed reporting line.
                Employee id=312, Dan Brown exceeds reporting line max depth by 1.
                ************************""";

        String actual = outputStreamCaptor.toString().trim().replace("\r", "");
        assertEquals(expected, actual);
    }


    @Test
    void processAnalyze_emptyEmployeesMapTest() {
        EmployeeAnalyzerService companyEmployeeAnalyzer = new EmployeeAnalyzerServiceImpl();
        companyEmployeeAnalyzer.processAnalyze(Map.of());

        assertEquals("", outputStreamCaptor.toString().trim());
    }


    private Map<Integer, Employee> getEmployeesMap() {
        if (employees == null) {
            employees = new HashMap<>();

            employees.put(123, new Employee(123, "Joe", "Doe", 60000, null, new ArrayList<>()));
            employees.put(124, new Employee(124, "Martin", "Chekov", 45000, 123, new ArrayList<>()));
            employees.put(125, new Employee(125, "Bob", "Chekov", 47000, 123, new ArrayList<>()));
            employees.put(300, new Employee(125, "Alice", "Hasacat", 50000, 124, new ArrayList<>()));
            employees.put(305, new Employee(305, "Brett", "Hardleaf", 34000, 300, new ArrayList<>()));
            employees.put(309, new Employee(309, "John", "Smith", 100000, 124, new ArrayList<>()));
            employees.put(310, new Employee(310, "Anna", "Smith", 100000, 309, new ArrayList<>()));
            employees.put(311, new Employee(311, "Anthony", "Brown", 100000, 310, new ArrayList<>()));
            employees.put(312, new Employee(312, "Dan", "Brown", 50000, 311, new ArrayList<>()));

        }
        return employees;
    }

    private void establishEmployeeManagementHierarchy() {
        for (Employee employee : employees.values()) {
            if (employee.managerId() != null) {
                Employee manager = employees.get(employee.managerId());
                manager.subordinates().add(employee);
            }
        }
    }
}
