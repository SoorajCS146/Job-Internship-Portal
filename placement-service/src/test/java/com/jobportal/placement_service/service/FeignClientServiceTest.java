package com.jobportal.placement_service.service;

import com.jobportal.placement_service.client.CompanyClient;
import com.jobportal.placement_service.client.StudentClient;
import com.jobportal.placement_service.dto.CompanyDTO;
import com.jobportal.placement_service.dto.StudentDTO;
import com.jobportal.placement_service.model.JobType;
import com.jobportal.placement_service.model.PlacementPosting;
import com.jobportal.placement_service.repository.PlacementPostingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeignClientServiceTest {

    private PlacementPostingRepository repo;
    private StudentClient studentClient;
    private CompanyClient companyClient;
    private PlacementPostingService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(PlacementPostingRepository.class);
        studentClient = Mockito.mock(StudentClient.class);
        companyClient = Mockito.mock(CompanyClient.class);
        service = new PlacementPostingService(repo, studentClient, companyClient);
    }

    // ---------- Sample PlacementPosting ----------
    private PlacementPosting samplePosting() {
        PlacementPosting p = new PlacementPosting();
        p.setId(1L);
        p.setTitle("Java Developer");
        p.setDescription("Java + Spring Boot role");
        p.setCompanyName("TechNova");
        p.setJobType(JobType.JOB);
        p.setLocation("Bangalore");
        p.setCtc(12.5);
        p.setEligibleBranches("CSE,ISE,AI");
        p.setMinimumCgpa(7.5);
        p.setApplicationDeadline(LocalDate.of(2025,12,31));
        return p;
    }

    // ---------- Sample StudentDTO ----------
    private StudentDTO sampleStudent() {
        StudentDTO s = new StudentDTO();
        s.setUsn("1MS23CS001");
        s.setName("Sooraj");
        s.setBranch("CSE");
        s.setCgpa(8.2);
        return s;
    }

    // ---------- TEST: Student Eligibility ----------
    @Test
    void isStudentEligible_returnsTrue() {
        PlacementPosting posting = samplePosting();
        StudentDTO student = sampleStudent();

        // Mock Feign call
        when(studentClient.getStudentByUsn("1MS23CS001")).thenReturn(student);

        // Mock repo call
        when(repo.findById(1L)).thenReturn(Optional.of(posting));

        boolean eligible = service.isStudentEligible("1MS23CS001", 1L);
        assertTrue(eligible);
    }

    @Test
    void isStudentEligible_returnsFalse_dueToCgpa() {
        PlacementPosting posting = samplePosting();
        StudentDTO student = sampleStudent();
        student.setCgpa(6.5); // below minimum

        when(studentClient.getStudentByUsn("1MS23CS001")).thenReturn(student);
        when(repo.findById(1L)).thenReturn(Optional.of(posting));

        assertFalse(service.isStudentEligible("1MS23CS001", 1L));
    }

    @Test
    void isStudentEligible_returnsFalse_dueToBranch() {
        PlacementPosting posting = samplePosting();
        StudentDTO student = sampleStudent();
        student.setBranch("ECE"); // not eligible

        when(studentClient.getStudentByUsn("1MS23CS001")).thenReturn(student);
        when(repo.findById(1L)).thenReturn(Optional.of(posting));

        assertFalse(service.isStudentEligible("1MS23CS001", 1L));
    }

    // ---------- TEST: Get postings by company ----------
    @Test
    void getPostingsByCompany_success() {
        CompanyDTO company = new CompanyDTO();
        company.setId(1L);
        company.setCompanyName("TechNova");

        PlacementPosting posting1 = samplePosting();
        PlacementPosting posting2 = samplePosting();
        posting2.setId(2L);

        when(companyClient.getCompanyById(1L)).thenReturn(company);
        when(repo.findByCompanyId(1L)).thenReturn(List.of(posting1, posting2));

        List<PlacementPosting> postings = service.getPostingsByCompany(1L);
        assertEquals(2, postings.size());
        assertEquals("TechNova", postings.get(0).getCompanyName());
    }
}

