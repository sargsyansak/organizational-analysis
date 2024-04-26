package com.company.organization.service.impl;

import com.company.organization.config.PropertiesLoader;
import com.company.organization.data.Employee;
import com.company.organization.data.EmployeeInfo;
import com.company.organization.data.SalaryInfo;
import com.company.organization.service.EmployeeAnalyzerService;
import com.company.organization.service.OutputService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class EmployeeAnalyzerServiceImpl implements EmployeeAnalyzerService {
    private final OutputService outputService;
    private final PropertiesLoader propertiesLoader;

    public EmployeeAnalyzerServiceImpl() {
        this.outputService = new ConsoleOutputServiceImpl();
        this.propertiesLoader = new PropertiesLoader();
    }

    @Override
    public void processAnalyze(Map<Integer, Employee> employees) {
        Optional<Employee> ceo = retrieveCeo(employees);
        ceo.ifPresent(manager -> {
            List<EmployeeInfo> employeeInfos = analyzeEmployeeHierarchy(manager, 0);
            outputService.outputSubordinates(employeeInfos, 0);
        });
    }

    private List<EmployeeInfo> analyzeEmployeeHierarchy(Employee manager, int reportingLineDepth) {
        List<EmployeeInfo> employeesInfo = new ArrayList<>();
        if (manager.hasSubordinates()) {
            EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setEmployeeIdentity(manager.identityInfo());

            SalaryInfo salaryInfo = analyzeSalary(manager);
            employeeInfo.setSalaryInfo(salaryInfo);
            employeesInfo.add(employeeInfo);

            if (reportingLineDepth > propertiesLoader.getReportingLineMaxDepth()) {
                employeeInfo.setExceedsReportingLineMaxDepth(true);
                employeeInfo.setExceedsReportingLineMaxDepthBy(reportingLineDepth - propertiesLoader.getReportingLineMaxDepth());
            }

            for (Employee subordinate : manager.subordinates()) {
                employeesInfo.addAll(analyzeEmployeeHierarchy(subordinate, reportingLineDepth + 1));
            }
        }
        return employeesInfo;
    }

    private SalaryInfo analyzeSalary(Employee currentEmployee) {
        double averageSalary = calculateAvgSalary(currentEmployee.subordinates());
        double minExpectedSalary = propertiesLoader.getMinExpectedCoefficient() * averageSalary;
        double maxExpectedSalary = propertiesLoader.getMaxExpectedCoefficient() * averageSalary;

        if (currentEmployee.salary() < minExpectedSalary) {
            return SalaryInfo.of(Integer.MIN_VALUE, minExpectedSalary - currentEmployee.salary());
        }

        if (currentEmployee.salary() > maxExpectedSalary) {
            return SalaryInfo.of(Integer.MAX_VALUE, currentEmployee.salary() - maxExpectedSalary);
        }
        return SalaryInfo.inRange();
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
}