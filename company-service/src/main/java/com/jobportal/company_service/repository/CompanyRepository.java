package com.jobportal.company_service.repository;

import com.jobportal.company_service.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyId(Long companyId);
}
