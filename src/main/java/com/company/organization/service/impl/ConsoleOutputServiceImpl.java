package com.company.organization.service.impl;

import com.company.organization.data.EmployeeInfo;
import com.company.organization.service.OutputService;

import java.util.List;

import static java.lang.System.out;

public class ConsoleOutputServiceImpl implements OutputService {

    public static final String LINE_DELIMITER = "************************";

    @Override
    public void outputSubordinates(List<EmployeeInfo> employeeInfos, int reportingLineDepth) {
        out.println(LINE_DELIMITER);
        out.println("Employees that earns less than expected");
        employeeInfos.stream().filter(EmployeeInfo::earnsLess).forEach(employeeInfo -> {
            String employeeInfoText = String.format("Employee %s earns less than expected by %s.",
                    employeeInfo.getEmployeeIdentity(),
                    employeeInfo.getSalaryInfo().getSalaryRangeDifference());
            out.println(employeeInfoText);
        });
        out.println(LINE_DELIMITER);
        out.println();
        out.println(LINE_DELIMITER);
        out.println("Employees that earns more than expected");
        employeeInfos.stream().filter(EmployeeInfo::earnsMore).forEach(employeeInfo -> {
            String employeeInfoText = String.format("Employee %s earns more than expected by %s.",
                    employeeInfo.getEmployeeIdentity(),
                    employeeInfo.getSalaryInfo().getSalaryRangeDifference());
            out.println(employeeInfoText);
        });
        out.println(LINE_DELIMITER);
        out.println();
        out.println(LINE_DELIMITER);
        out.println("Employees that exceeds max allowed reporting line.");
        List<EmployeeInfo> employeesWithHighReportingLine = employeeInfos.stream()
                .filter(EmployeeInfo::isExceedsReportingLineMaxDepth)
                .toList();
        if (employeesWithHighReportingLine.isEmpty()) {
            out.println("There are no employees who exceeds reporting line max depth. All good!!!");
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
}