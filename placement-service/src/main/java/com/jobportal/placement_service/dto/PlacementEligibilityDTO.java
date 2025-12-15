package com.jobportal.placement_service.dto;

public class PlacementEligibilityDTO {

    private StudentDTO student;
    private CompanyDTO company;

    public PlacementEligibilityDTO() {}

    public PlacementEligibilityDTO(StudentDTO student, CompanyDTO company) {
        this.student = student;
        this.company = company;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }
}
