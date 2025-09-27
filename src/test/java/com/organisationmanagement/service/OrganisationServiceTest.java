package com.organisationmanagement.service;


import com.organisationmanagement.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrganizationServiceTest {

    private OrganizationService service;

    @BeforeEach
    void setUp() {
        service = new OrganizationService();
    }

    @Test
    void testManagerEarningTooLow() {
        Employee manager = new Employee(1, "Boss", "Man", 100, null, new ArrayList());
        Employee emp1 = new Employee(2, "Worker", "One", 100, 1, new ArrayList());
        Employee emp2 = new Employee(3, "Worker", "Two", 100, 1, new ArrayList());

        manager.getSubordinates().addAll(List.of(emp1, emp2));

        List<String> results = service.validateManagerSalaries(List.of(manager, emp1, emp2));
        assertEquals(1, results.size());
        assertTrue(results.get(0).contains("LESS"));
    }

    @Test
    void testManagerEarningTooHigh() {
        Employee manager = new Employee(1, "Boss", "Man", 1000, null, new ArrayList());
        Employee emp = new Employee(2, "Worker", "One", 100, 1, new ArrayList());

        manager.getSubordinates().add(emp);

        List<String> results = service.validateManagerSalaries(List.of(manager, emp));
        assertEquals(1, results.size());
        assertTrue(results.get(0).contains("MORE"));
    }

    @Test
    void testManagerEarningWithinRange() {
        Employee manager = new Employee(1, "Boss", "Man", 140, null, new ArrayList());
        Employee emp1 = new Employee(2, "Worker", "One", 100, 1, new ArrayList());
        Employee emp2 = new Employee(3, "Worker", "Two", 100, 1, new ArrayList());

        manager.getSubordinates().addAll(List.of(emp1, emp2));

        List<String> results = service.validateManagerSalaries(List.of(manager, emp1, emp2));
        assertTrue(results.isEmpty());
    }

    @Test
    void testReportingLineTooLong() {
        Employee ceo = new Employee(1, "CEO", "Top", 1000, null, new ArrayList());
        Employee a = new Employee(2, "A", "L1", 900, 1, new ArrayList());
        Employee b = new Employee(3, "B", "L2", 800, 2, new ArrayList());
        Employee c = new Employee(4, "C", "L3", 700, 3, new ArrayList());
        Employee d = new Employee(5, "D", "L4", 600, 4, new ArrayList());
        Employee e = new Employee(6, "E", "L5", 500, 5, new ArrayList());

        ceo.getSubordinates().add(a);
        a.getSubordinates().add(b);
        b.getSubordinates().add(c);
        c.getSubordinates().add(d);
        d.getSubordinates().add(e);

        List<String> results = service.validateReportingLines(ceo);
        assertEquals(1, results.size());
        assertTrue(results.get(0).contains("too long"));
    }

    @Test
    void testReportingLineWithinLimit() {
        Employee ceo = new Employee(1, "CEO", "Top", 1000, null, new ArrayList());
        Employee a = new Employee(2, "A", "L1", 900, 1, new ArrayList());
        Employee b = new Employee(3, "B", "L2", 800, 2, new ArrayList());
        Employee c = new Employee(4, "C", "L3", 700, 3, new ArrayList());

        ceo.getSubordinates().add(a);
        a.getSubordinates().add(b);
        b.getSubordinates().add(c);

        List<String> results = service.validateReportingLines(ceo);
        assertTrue(results.isEmpty());
    }
}
