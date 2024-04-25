package com.company.organization.service;

import com.company.organization.data.Employee;

/**
 * An interface for services that output information about an employee's subordinates.
 */
public interface OutputService {

    /**
     * Outputs information about an employee's subordinates.
     *
     * @param employee           The employee whose subordinates are to be outputted.
     * @param reportingLineDepth The depth of the reporting line for the employee's subordinates.
     */
    void outputSubordinates(Employee employee, int reportingLineDepth);
}
