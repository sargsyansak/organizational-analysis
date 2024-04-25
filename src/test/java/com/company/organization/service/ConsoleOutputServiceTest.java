package com.company.organization.service;

import com.company.organization.config.PropertiesLoader;
import com.company.organization.data.Employee;
import com.company.organization.service.impl.ConsoleOutputService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleOutputServiceTest {

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void outputSubordinatesTest() {
        Employee subordinate = new Employee(3, "Tom", "Dimm", 3000, 2);

        Employee employee = new Employee(2, "Jane", "Doe", 4000, 1);
        employee.subordinates().add(subordinate);

        ConsoleOutputService service = new ConsoleOutputService();
        int reportingLineDepth = 3;

        service.outputSubordinates(employee, reportingLineDepth);

        StringBuilder expectedOutput = new StringBuilder("Find below Employees with reporting line more by 1" + System.lineSeparator());

        for(Employee emp: employee.subordinates()){
            expectedOutput.append(emp.identityInfo()).append(System.lineSeparator());
        }

        System.out.println("Expected:");
        System.out.println(expectedOutput);

        System.out.println("Actual:");
        System.out.println(outContent.toString());

        assertTrue(outContent.toString().contains(expectedOutput.toString()), "The actual output should contain the expected output");
    }
}