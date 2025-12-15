package com.jobportal.company_service.service;

import com.jobportal.company_service.dto.CompanyRequestDto;
import com.jobportal.company_service.dto.CompanyResponseDto;
import com.jobportal.company_service.exception.CompanyNotFoundException;
import com.jobportal.company_service.model.Company;
import com.jobportal.company_service.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company company;
    private CompanyRequestDto requestDto;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setId(1L);
        company.setCompanyName("Google");
        company.setIndustry("Tech");
        company.setDescription("Search Engine");
        company.setWebsite("https://google.com");
        company.setContactEmail("hr@google.com");

        requestDto = new CompanyRequestDto();
        requestDto.companyName = "Google";
        requestDto.industry = "Tech";
        requestDto.description = "Search Engine";
        requestDto.website = "https:/google.com";
        requestDto.contactEmail = "hr@google.com";
    }

    // CREATION test.
    @Test
    void createCompany_shouldReturnSavedCompany() {
        when(companyRepository.save(any(Company.class)))
                .thenReturn(company);

        CompanyResponseDto response = companyService.createCompany(requestDto);

        assertNotNull(response);
        assertEquals("Google", response.companyName);
        verify(companyRepository, times(1)).save(any(Company.class));
    }

    // GET-BY-ID test.
    @Test
    void getCompanyById_whenFound_shouldReturnCompany() {
        when(companyRepository.findById(1L))
                .thenReturn(Optional.of(company));

        CompanyResponseDto response = companyService.getCompanyById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id);
    }

    @Test
    void getCompanyById_whenNotFound_shouldThrowException() {
        when(companyRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                CompanyNotFoundException.class,
                () -> companyService.getCompanyById(1L)
        );
    }

    // ---------------- GET ALL ----------------

    @Test
    void getAllCompanies_shouldReturnList() {
        when(companyRepository.findAll())
                .thenReturn(List.of(company));

        List<CompanyResponseDto> companies =
                companyService.getAllCompanies();

        assertEquals(1, companies.size());
        assertEquals("Google", companies.get(0).companyName);
    }

    // ---------------- UPDATE ----------------

    @Test
    void updateCompany_whenFound_shouldUpdateAndReturn() {
        when(companyRepository.findById(1L))
                .thenReturn(Optional.of(company));
        when(companyRepository.save(any(Company.class)))
                .thenReturn(company);

        CompanyResponseDto response =
                companyService.updateCompany(1L, requestDto);

        assertNotNull(response);
        assertEquals("Google", response.companyName);
    }

    @Test
    void updateCompany_whenNotFound_shouldReturnNull() {
        when(companyRepository.findById(1L))
                .thenReturn(Optional.empty());

        CompanyResponseDto response =
                companyService.updateCompany(1L, requestDto);

        assertNull(response);
        verify(companyRepository, never()).save(any());
    }

    // ---------------- DELETE ----------------

    @Test
    void deleteCompanyById_whenExists_shouldDelete() {
        when(companyRepository.existsById(1L))
                .thenReturn(true);

        companyService.deleteCompanyById(1L);

        verify(companyRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCompanyById_whenNotExists_shouldThrowException() {
        when(companyRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(
                CompanyNotFoundException.class,
                () -> companyService.deleteCompanyById(1L)
        );
    }
}
