package com.jobportal.company_service.service;

import com.jobportal.company_service.dto.CompanyRequestDto;
import com.jobportal.company_service.dto.CompanyResponseDto;

import java.util.List;

public interface CompanyService {
    CompanyResponseDto createCompany(CompanyRequestDto request);

    CompanyResponseDto getCompanyById(Long id);
    List<CompanyResponseDto> getAllCompanies();

    CompanyResponseDto updateCompany(Long id, CompanyRequestDto requestDto);

    void deleteCompanyById(Long id);
    void deleteAllCompanies();

}
