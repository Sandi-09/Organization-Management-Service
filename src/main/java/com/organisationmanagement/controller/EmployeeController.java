package com.organisationmanagement.controller;

import com.organisationmanagement.model.Employee;
import com.organisationmanagement.utilis.CsvParserService;
import com.organisationmanagement.service.OrganizationService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final OrganizationService organizationService;

    @PostMapping("/analyze")
    public Map<String, Object> analyzeCsv(@RequestParam("file") MultipartFile file) throws Exception {
        Map<Integer, Employee> employees = CsvParserService.parseEmployees(file);

        Employee ceo = organizationService.findCeo(employees.values());

        Map<String, Object> response = new HashMap<>();
        response.put("salaryViolations", organizationService.validateManagerSalaries(employees.values()));
        response.put("reportingViolations", organizationService.validateReportingLines(ceo));
        response.put("ceo ", ceo.getFirstName()+" "+ceo.getLastName());

        return response;
    }

    @GetMapping("/hello")
    public String testApi(){
        return "Hello World";
    }
}