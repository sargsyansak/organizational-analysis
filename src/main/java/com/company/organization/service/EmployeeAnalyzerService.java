package com.company.organization.service;

import com.company.organization.data.Employee;

import java.util.Map;

public interface EmployeeAnalyzerService {

    /**
     * Analyzes employees starting from the CEO down to their subordinates in a recursive manner.
     * If the CEO does not exist, the function simply returns.
     *
     * @param employees map of employee ID to Employee objects representing all employees in the company
     */

    void processAnalyze(Map<Integer, Employee> employees);
}