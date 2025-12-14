package com.jobportal.company_service.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(Long id) {
        super("Company not found with ID: " + id);
    }
}
