package com.organisationmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.organisationmanagement")
public class OrganisationManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganisationManagementApplication.class, args);
    }

}
