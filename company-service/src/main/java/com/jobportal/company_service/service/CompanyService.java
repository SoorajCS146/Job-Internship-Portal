package com.jobportal.company_service.service;

import com.jobportal.company_service.dto.CompanyRequestDto;
import com.jobportal.company_service.dto.CompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    CompanyResponseDto createCompany(CompanyRequestDto request);

    CompanyResponseDto getCompanyById(Long id);
//    Page<CompanyResponseDto> getAllCompanies(Pageable pageable);
    List<CompanyResponseDto> getAllCompanies();

    CompanyResponseDto updateCompany(Long id, CompanyRequestDto requestDto);

    void deleteCompanyById(Long id);
    void deleteAllCompanies();

}
