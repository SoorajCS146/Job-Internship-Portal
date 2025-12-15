package com.jobportal.placement_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.placement_service.client.CompanyClient;
import com.jobportal.placement_service.client.StudentClient;
import com.jobportal.placement_service.dto.CompanyDTO;
import com.jobportal.placement_service.dto.PlacementEligibilityDTO;
import com.jobportal.placement_service.dto.StudentDTO;
import com.jobportal.placement_service.model.JobType;
import com.jobportal.placement_service.model.PlacementPosting;
import com.jobportal.placement_service.service.PlacementPostingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Import({
        PlacementPostingController.class,
        FeignClientControllerTest.TestConfig.class
})
class FeignClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlacementPostingService service;

    @Autowired
    private StudentClient studentClient;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ObjectMapper objectMapper;

    // Test Configuration
    @Configuration
    static class TestConfig {
        @Bean
        PlacementPostingService service() { return Mockito.mock(PlacementPostingService.class); }

        @Bean
        StudentClient studentClient() { return Mockito.mock(StudentClient.class); }

        @Bean
        CompanyClient companyClient() { return Mockito.mock(CompanyClient.class); }
    }

    // Sample Posting
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

    private StudentDTO sampleStudent() {
        StudentDTO s = new StudentDTO();
        s.setUsn("1MS23CS001");
        s.setName("Sooraj");
        s.setBranch("CSE");
        s.setCgpa(8.2);
        return s;
    }

    private CompanyDTO sampleCompany() {
        CompanyDTO c = new CompanyDTO();
        c.setId(1L);
        c.setCompanyName("TechNova");
        return c;
    }

    // CREATE
    @Test
    void createPosting_success() throws Exception {
        PlacementPosting posting = samplePosting();
        when(service.createPosting(any(PlacementPosting.class))).thenReturn(posting);

        mockMvc.perform(post("/placements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(posting)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Developer"));
    }

    // GET ALL
    @Test
    void getAllPostings_success() throws Exception {
        when(service.getAllPostings()).thenReturn(List.of(samplePosting()));

        mockMvc.perform(get("/placements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // GET ONE
    @Test
    void getPosting_success() throws Exception {
        when(service.getPosting(1L)).thenReturn(samplePosting());

        mockMvc.perform(get("/placements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // UPDATE
    @Test
    void updatePosting_success() throws Exception {
        PlacementPosting updated = samplePosting();
        updated.setTitle("Updated Title");
        when(service.updatePosting(Mockito.eq(1L), any(PlacementPosting.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/placements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    // DELETE
    @Test
    void deletePosting_success() throws Exception {
        mockMvc.perform(delete("/placements/1"))
                .andExpect(status().isOk());
    }

    // CHECK STUDENT ELIGIBILITY
    @Test
    void checkStudentEligibility_success() throws Exception {
        when(service.isStudentEligible("1MS23CS001", 1L)).thenReturn(true);

        mockMvc.perform(get("/placements/check-eligibility/1MS23CS001/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // GET POSTINGS BY COMPANY
    @Test
    void getPostingsByCompany_success() throws Exception {
        when(service.getPostingsByCompany(1L)).thenReturn(List.of(samplePosting()));

        mockMvc.perform(get("/placements/company/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // GET COMBINED STUDENT+COMPANY ELIGIBILITY
    @Test
    void getPlacementEligibility_of_Student_Company_Pair_success() throws Exception {
        StudentDTO student = sampleStudent();
        CompanyDTO company = sampleCompany();

        when(studentClient.getStudentById(1L)).thenReturn(student);
        when(companyClient.getCompanyById(1L)).thenReturn(company);

        mockMvc.perform(get("/placements/eligibility/student/1/company/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.name").value("Sooraj"))
                .andExpect(jsonPath("$.company.companyName").value("TechNova"));
    }
}
