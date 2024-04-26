package com.company.organization.data;

public class SalaryInfo {
    /**
     * if the number is negative then salary is less than the range,
     * in case the number is positive then salary is more than the range.
     * in case of 0 the salary is in range.
     */
    private final int isInSalaryRange;
    private final double salaryRangeDifference;

    public static SalaryInfo of(int isInSalaryRange, double salaryRangeDifference) {
        return new SalaryInfo(isInSalaryRange, salaryRangeDifference);
    }

    public static SalaryInfo inRange() {
        return new SalaryInfo(0, 0);
    }

    private SalaryInfo(int isInSalaryRange, double salaryRangeDifference) {
        this.isInSalaryRange = isInSalaryRange;
        this.salaryRangeDifference = salaryRangeDifference;
    }

    public int getIsInSalaryRange() {
        return isInSalaryRange;
    }

    public double getSalaryRangeDifference() {
        return salaryRangeDifference;
    }
}