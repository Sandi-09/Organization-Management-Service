package com.organisationmanagement.service;

import com.organisationmanagement.model.Employee;
import com.organisationmanagement.model.EmployeeDepth;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrganizationService {
    public Employee findCeo(Collection<Employee> employees) {
        return employees.stream()
                .filter(e -> e.getManagerId() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No CEO found"));
    }

    public List<String> validateManagerSalaries(Collection<Employee> employees) {
        List<String> results = new ArrayList<>();

        for (Employee manager : employees) {
            if (manager.getSubordinates().isEmpty()) continue;

            double avg = manager.getSubordinates().stream()
                    .mapToDouble(Employee::getSalary)
                    .average().orElse(0);

            double minAllowed = avg * 1.2;
            double maxAllowed = avg * 1.5;

            if (manager.getSalary() < minAllowed) {
                results.add(manager + " earns LESS than required by " + (minAllowed - manager.getSalary()));
            } else if (manager.getSalary() > maxAllowed) {
                results.add(manager + " earns MORE than allowed by " + (manager.getSalary() - maxAllowed));
            }
        }
        return results;
    }

    public List<String> validateReportingLines(Employee ceo) {
        List<String> validatedReportees = new ArrayList<>();

        Queue<EmployeeDepth> validEmployeeQueue = new LinkedList();
        validEmployeeQueue.offer(new EmployeeDepth(ceo,0));

        while(!validEmployeeQueue.isEmpty()){
            EmployeeDepth employeeDepthHirarchy = validEmployeeQueue.poll();
            Employee emp = employeeDepthHirarchy.getEmployee();
            int depth = employeeDepthHirarchy.getDepth();
            if (depth > 4) {
                validatedReportees.add(emp + " has reporting line too long by " + (depth - 4));
            }
            for (Employee sub : emp.getSubordinates()) {
                validEmployeeQueue.add(new EmployeeDepth(sub, depth + 1));
            }

        }

        return validatedReportees;
    }
}
