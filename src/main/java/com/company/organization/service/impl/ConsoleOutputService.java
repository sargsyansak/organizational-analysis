package com.company.organization.service.impl;

import com.company.organization.config.PropertiesLoader;
import com.company.organization.data.Employee;
import com.company.organization.service.OutputService;

import static java.lang.System.out;

public class ConsoleOutputService implements OutputService {

    private final PropertiesLoader propertiesLoader;

    public ConsoleOutputService() {
        this.propertiesLoader = new PropertiesLoader();
    }

    @Override
    public void outputSubordinates(Employee employee, int reportingLineDepth) {
        String message = String.format("Find below Employees with reporting line more by %d", reportingLineDepth - propertiesLoader.getReportingLineMaxDepth());
        out.println(message);
        employee.subordinates().stream()
                .map(Employee::identityInfo)
                .forEach(out::println);
    }
}