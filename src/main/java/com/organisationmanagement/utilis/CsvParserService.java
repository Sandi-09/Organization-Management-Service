package com.organisationmanagement.utilis;

import com.organisationmanagement.model.Employee;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/*
this is a utility service to create the utility for the csv parsing that will be done through the
rest controller
 */


@UtilityClass
public class CsvParserService {

    public static Map<Integer, Employee> parseEmployees(MultipartFile file) throws Exception {
        Map<Integer, Employee> employees = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String firstName = parts[1].trim();
                String lastName = parts[2].trim();
                double salary = Double.parseDouble(parts[3].trim());
                Integer managerId = parts.length > 4 && !parts[4].trim().isEmpty()
                        ? Integer.parseInt(parts[4].trim()) : null;

                Employee employee = Employee.builder()
                        .id(id)
                        .firstName(firstName)
                        .lastName(lastName)
                        .salary(salary)
                        .managerId(managerId)
                        .build();

                employees.put(id, employee);
            }
        }

        // Build hierarchy
        for (Employee emp : employees.values()) {
            if (emp.getManagerId() != null) {
                employees.get(emp.getManagerId()).addSubordinate(emp);
            }
        }

        return employees;
    }
}

