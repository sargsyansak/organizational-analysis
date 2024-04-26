package com.company.organization.service;

import com.company.organization.data.EmployeeInfo;

import java.util.List;

public interface OutputService {

    /**
     * The method is used for outputting the information about subordinates.
     * <p>
     * The information includes whether the subordinates earn less or more than expected and if any subordinate exceeds
     * the maximum allowed reporting line depth.
     *
     * @param employeeInfos      The list of EmployeeInfo objects, each representing a different subordinate.
     * @param reportingLineDepth The maximum depth of the reporting line.
     */
    void outputSubordinates(List<EmployeeInfo> employeeInfos, int reportingLineDepth);
}