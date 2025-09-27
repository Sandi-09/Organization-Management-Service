package com.organisationmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDepth {
    private Employee employee;
    private int depth;
}
