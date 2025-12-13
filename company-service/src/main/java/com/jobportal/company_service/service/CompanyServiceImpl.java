package com.jobportal.company_service.service;

import com.jobportal.company_service.dto.CompanyRequestDto;
import com.jobportal.company_service.dto.CompanyResponseDto;
import com.jobportal.company_service.model.Company;
import com.jobportal.company_service.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    // constructor-injection.
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyResponseDto createCompany(CompanyRequestDto request) {
        Company company = new Company();
        company.setCompanyName(request.companyName);
        company.setIndustry(request.industry);
        company.setDescription(request.description);
        company.setWebsite(request.website);
        company.setContactEmail(request.contactEmail);
        company.setContactPhone(request.contactPhone);

        Company saved = companyRepository.save(company);
        return mapToResponse(saved);
    }

    @Override
    public CompanyResponseDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElse(null);
//                .orElseThrow(() -> new RuntimeException("Company not found"));
        if(company != null)
            return mapToResponse(company);
        return null;
    }

    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CompanyResponseDto mapToResponse(Company company) {
        CompanyResponseDto dto = new CompanyResponseDto();
        dto.id = company.getId();
        dto.companyName = company.getCompanyName();
        dto.industry = company.getIndustry();
        dto.description = company.getDescription();
        dto.website = company.getWebsite();
        dto.contactEmail = company.getContactEmail();
//        dto.contactPhone = company.getContactPhone();

        return dto;
    }

}
