package com.company.organization.service.impl;

import com.company.organization.config.PropertiesLoader;
import com.company.organization.data.Employee;
import com.company.organization.service.EmployeeAnalyzerService;
import com.company.organization.service.OutputService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static java.lang.System.out;

public class EmployeeAnalyzerServiceImpl implements EmployeeAnalyzerService {
    private final Logger logger;
    private final OutputService outputService;
    private final PropertiesLoader propertiesLoader;

    public EmployeeAnalyzerServiceImpl() {
        this.outputService = new ConsoleOutputService();
        this.propertiesLoader = new PropertiesLoader();
        this.logger = Logger.getLogger(EmployeeAnalyzerServiceImpl.class.getName());
    }

    @Override
    public void processAnalyze(Map<Integer, Employee> employees) {
        Optional<Employee> ceo = retrieveCeo(employees);
        ceo.ifPresent(manager -> analyzeEmployeeHierarchy(manager, 0));
    }

    private void analyzeEmployeeHierarchy(Employee manager, int reportingLineDepth) {
        if (manager.hasSubordinates()) {
            analyzeSalary(manager);

            if (reportingLineDepth > propertiesLoader.getReportingLineMaxDepth()) {
                outputService.outputSubordinates(manager, reportingLineDepth);
            }

            for (Employee subordinate : manager.subordinates()) {
                analyzeEmployeeHierarchy(subordinate, reportingLineDepth + 1);
            }
        }
    }

    private void analyzeSalary(Employee currentEmployee) {
        double averageSalary = calculateAvgSalary(currentEmployee.subordinates());
        double minExpectedSalary = propertiesLoader.getMinExpectedCoefficient() * averageSalary;
        double maxExpectedSalary = propertiesLoader.getMaxExpectedCoefficient() * averageSalary;

        if (currentEmployee.salary() < minExpectedSalary) {
            logAndPrintWarning(String.format("Employee id=%s, %s earns less than expected by %s.",
                    currentEmployee.id(),
                    currentEmployee.getFullName(),
                    minExpectedSalary - currentEmployee.salary()));
        }

        if (currentEmployee.salary() > maxExpectedSalary) {
            logAndPrintWarning(String.format("Employee id=%s, %s earns more than expected by %s.",
                    currentEmployee.id(),
                    currentEmployee.getFullName(),
                    currentEmployee.salary() - maxExpectedSalary));
        }
    }

    private double calculateAvgSalary(List<Employee> employees) {
        return employees.stream()
                .mapToDouble(Employee::salary).average()
                .orElse(0);
    }

    private Optional<Employee> retrieveCeo(Map<Integer, Employee> employees) {
        return employees.values().stream()
                .filter(e -> e.managerId() == null)
                .findFirst();
    }

    private void logAndPrintWarning(String message) {
        logger.warning(message);
        out.println(message);
    }
}