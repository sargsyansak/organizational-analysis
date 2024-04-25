package com.company.organization.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Employee(
        int id,
        String firstName,
        String lastName,
        int salary,
        Integer managerId,
        List<Employee> subordinates) {

    public Employee(int id,
                    String firstName,
                    String lastName,
                    int salary,
                    Integer managerId) {
        this(id, firstName, lastName, salary, managerId, new ArrayList<>());
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String identityInfo() {
        return "id=" + id + ", " + getFullName();
    }

    public boolean hasSubordinates() {
        return subordinates != null && !subordinates.isEmpty();
    }

    public Employee copyImmutable() {
        return new Employee(id, firstName, lastName, salary, managerId, Collections.unmodifiableList(subordinates));
    }
}