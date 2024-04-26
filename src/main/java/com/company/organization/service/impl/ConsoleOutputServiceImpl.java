package com.company.organization.service.impl;

import com.company.organization.data.EmployeeInfo;
import com.company.organization.service.OutputService;

import java.util.List;

import static java.lang.System.out;

public class ConsoleOutputServiceImpl implements OutputService {

    public static final String LINE_DELIMITER = "************************";

    @Override
    public void outputSubordinates(List<EmployeeInfo> employeeInfos) {
        out.println(LINE_DELIMITER);
        out.println("Employees that earns less than expected");
        List<EmployeeInfo> employeesEarnsLess = employeeInfos.stream().filter(EmployeeInfo::earnsLess).toList();
        if (employeesEarnsLess.isEmpty()) {
            printEmptyResultMessage();
        } else {
            employeesEarnsLess.forEach(employeeInfo -> {
                String employeeInfoText = String.format("Employee %s earns less than expected by %s.",
                        employeeInfo.getEmployeeIdentity(),
                        employeeInfo.getSalaryInfo().getSalaryRangeDifference());
                out.println(employeeInfoText);
            });
        }
        out.println(LINE_DELIMITER);
        out.println();
        out.println(LINE_DELIMITER);
        out.println("Employees that earns more than expected");
        List<EmployeeInfo> employeesEarnsMore = employeeInfos.stream().filter(EmployeeInfo::earnsMore).toList();
        if (employeesEarnsMore.isEmpty()) {
            printEmptyResultMessage();
        } else {
            employeesEarnsMore.forEach(employeeInfo -> {
                String employeeInfoText = String.format("Employee %s earns more than expected by %s.",
                        employeeInfo.getEmployeeIdentity(),
                        employeeInfo.getSalaryInfo().getSalaryRangeDifference());
                out.println(employeeInfoText);
            });
        }
        out.println(LINE_DELIMITER);
        out.println();
        out.println(LINE_DELIMITER);
        out.println("Employees that exceeds max allowed reporting line.");
        List<EmployeeInfo> employeesWithHighReportingLine = employeeInfos.stream()
                .filter(EmployeeInfo::isExceedsReportingLineMaxDepth)
                .toList();
        if (employeesWithHighReportingLine.isEmpty()) {
            printEmptyResultMessage();
        } else {
            employeesWithHighReportingLine.forEach(employeeInfo -> {
                String employeeInfoText = String.format("Employee %s exceeds reporting line max depth by %s.",
                        employeeInfo.getEmployeeIdentity(),
                        employeeInfo.getExceedsReportingLineMaxDepthBy());
                out.println(employeeInfoText);
            });
        }

        out.println(LINE_DELIMITER);
    }

    private static void printEmptyResultMessage() {
        out.println("There are no employees in this category. All good!!!");
    }
}