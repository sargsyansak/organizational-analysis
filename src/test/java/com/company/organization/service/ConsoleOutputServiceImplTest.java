package com.company.organization.service;

import com.company.organization.data.EmployeeInfo;
import com.company.organization.data.SalaryInfo;
import com.company.organization.service.impl.ConsoleOutputServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test class for ConsoleOutputServiceImpl.
 * The setUp() method, executed before each test, redirects System.out to capture and analyze the console output.
 * The tearDown() method, executed after each test, restores the standard System.out.
 * The outputSubordinates_outputsExpectedResultsTest() method verifies the output matches the expected values for the given test data.
 * The buildExpectedOutput() method generates the expected console output for comparison in the tests.
 */
class ConsoleOutputServiceImplTest {

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
    void outputSubordinates_outputsExpectedResultsTest() {

        SalaryInfo salaryInfoLess = SalaryInfo.of(-1, 5000);
        SalaryInfo salaryInfoMore = SalaryInfo.of(1, 7000);
        SalaryInfo salaryInfoInRange = SalaryInfo.inRange();

        EmployeeInfo employeeEarnsLess = new EmployeeInfo();
        employeeEarnsLess.setEmployeeIdentity("John Doe");
        employeeEarnsLess.setSalaryInfo(salaryInfoLess);
        employeeEarnsLess.setExceedsReportingLineMaxDepth(false);

        EmployeeInfo employeeEarnsMore = new EmployeeInfo();
        employeeEarnsMore.setEmployeeIdentity("Jane Doe");
        employeeEarnsMore.setSalaryInfo(salaryInfoMore);
        employeeEarnsMore.setExceedsReportingLineMaxDepth(false);

        EmployeeInfo employeeInRange = new EmployeeInfo();
        employeeInRange.setEmployeeIdentity("In-Range Employee");
        employeeInRange.setSalaryInfo(salaryInfoInRange);
        employeeInRange.setExceedsReportingLineMaxDepth(true);
        employeeInRange.setExceedsReportingLineMaxDepthBy(2);

        List<EmployeeInfo> employeeInfos = List.of(employeeEarnsLess, employeeEarnsMore, employeeInRange);

        ConsoleOutputServiceImpl service = new ConsoleOutputServiceImpl();
        service.outputSubordinates(employeeInfos, 2);

        String expectedOutput = buildExpectedOutput(employeeInfos);
        assertEquals(expectedOutput, outContent.toString());
    }

    private String buildExpectedOutput(List<EmployeeInfo> employeeInfos) {
        String ls = System.lineSeparator(); // Line separator, e.g., \n on Unix, \r\n on Windows.
        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append("************************").append(ls);
        expectedOutput.append("Employees that earns less than expected").append(ls);
        employeeInfos.stream()
                .filter(EmployeeInfo::earnsLess)
                .forEach(employeeInfo -> {
                    String employeeInfoText = String.format("Employee %s earns less than expected by %s.",
                            employeeInfo.getEmployeeIdentity(),
                            employeeInfo.getSalaryInfo().getSalaryRangeDifference());
                    expectedOutput.append(employeeInfoText).append(ls);
                });
        expectedOutput.append("************************").append(ls).append(ls);
        expectedOutput.append("************************").append(ls);
        expectedOutput.append("Employees that earns more than expected").append(ls);
        employeeInfos.stream()
                .filter(EmployeeInfo::earnsMore)
                .forEach(employeeInfo -> {
                    String employeeInfoText = String.format("Employee %s earns more than expected by %s.",
                            employeeInfo.getEmployeeIdentity(),
                            employeeInfo.getSalaryInfo().getSalaryRangeDifference());
                    expectedOutput.append(employeeInfoText).append(ls);
                });
        expectedOutput.append("************************").append(ls).append(ls);
        expectedOutput.append("************************").append(ls);
        expectedOutput.append("Employees that exceeds max allowed reporting line.").append(ls);
        List<EmployeeInfo> employeesWithHighReportingLine = employeeInfos.stream()
                .filter(EmployeeInfo::isExceedsReportingLineMaxDepth)
                .toList();
        if (employeesWithHighReportingLine.isEmpty()) {
            expectedOutput.append("There are no employees who exceeds reporting line max depth. All good!!!").append(ls);
        } else {
            employeesWithHighReportingLine.forEach(employeeInfo -> {
                String employeeInfoText = String.format("Employee %s exceeds reporting line max depth by %s.",
                        employeeInfo.getEmployeeIdentity(),
                        employeeInfo.getExceedsReportingLineMaxDepthBy());
                expectedOutput.append(employeeInfoText).append(ls);
            });
        }
        expectedOutput.append("************************").append(ls);

        return expectedOutput.toString();
    }
}