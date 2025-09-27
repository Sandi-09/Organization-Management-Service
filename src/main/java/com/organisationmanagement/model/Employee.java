package com.organisationmanagement.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private double salary;
    private Integer managerId;

    @Builder.Default
    private List<Employee> subordinates = new ArrayList<>();

    public void addSubordinate(Employee e) {
        subordinates.add(e);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (ID: " + id + ")";
    }
}
