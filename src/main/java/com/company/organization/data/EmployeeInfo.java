package com.company.organization.data;

public class EmployeeInfo {

    private String employeeIdentity;
    private boolean exceedsReportingLineMaxDepth;
    private int exceedsReportingLineMaxDepthBy;
    private SalaryInfo salaryInfo;

    public boolean earnsLess() {
        return salaryInfo.getIsInSalaryRange() < 0;
    }

    public boolean earnsMore() {
        return salaryInfo.getIsInSalaryRange() > 0;
    }

    public String getEmployeeIdentity() {
        return employeeIdentity;
    }

    public void setEmployeeIdentity(String employeeIdentity) {
        this.employeeIdentity = employeeIdentity;
    }

    public boolean isExceedsReportingLineMaxDepth() {
        return exceedsReportingLineMaxDepth;
    }

    public void setExceedsReportingLineMaxDepth(boolean exceedsReportingLineMaxDepth) {
        this.exceedsReportingLineMaxDepth = exceedsReportingLineMaxDepth;
    }

    public int getExceedsReportingLineMaxDepthBy() {
        return exceedsReportingLineMaxDepthBy;
    }

    public void setExceedsReportingLineMaxDepthBy(int exceedsReportingLineMaxDepthBy) {
        this.exceedsReportingLineMaxDepthBy = exceedsReportingLineMaxDepthBy;
    }

    public SalaryInfo getSalaryInfo() {
        return salaryInfo;
    }

    public void setSalaryInfo(SalaryInfo salaryInfo) {
        this.salaryInfo = salaryInfo;
    }
}
