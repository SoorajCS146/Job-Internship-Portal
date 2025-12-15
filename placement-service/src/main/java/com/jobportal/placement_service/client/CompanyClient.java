package com.jobportal.placement_service.client;

import com.jobportal.placement_service.dto.CompanyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyClient {

    @GetMapping("/companies/{id}")
    CompanyDTO getCompanyById(@PathVariable("id") Long id);

    @GetMapping("/companies")
    List<CompanyDTO> getAllCompanies();
}