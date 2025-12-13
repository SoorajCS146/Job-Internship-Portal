package com.jobportal.company_service.controller;

import com.jobportal.company_service.dto.CompanyRequestDto;
import com.jobportal.company_service.dto.CompanyResponseDto;
import com.jobportal.company_service.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

//    @GetMapping
//    public String greetMsg() {
//        return "Welcome to the \"company-service\" Service!";
//    }

    @PostMapping
    public CompanyResponseDto create(@RequestBody CompanyRequestDto requestDto) {
        return companyService.createCompany(requestDto);
    }

    @GetMapping("/{id}")
    public CompanyResponseDto getById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping
    public List<CompanyResponseDto> getAll() {
        return companyService.getAllCompanies();
    }
}
