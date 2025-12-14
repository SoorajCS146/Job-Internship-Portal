package com.jobportal.company_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CompanyRequestDto {

    @NotBlank
    public String companyName;

    public String industry;

    @Size(max = 500)
    public String description;

    @Pattern(regexp = "https?://.*")
    public String website;

    @Email
    @NotBlank
    public String contactEmail;

    public String contactPhone;
}
