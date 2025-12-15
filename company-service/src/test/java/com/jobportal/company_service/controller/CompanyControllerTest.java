package com.jobportal.company_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.company_service.dto.CompanyRequestDto;
import com.jobportal.company_service.dto.CompanyResponseDto;
import com.jobportal.company_service.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- CREATE ----------

    @Test
    void createCompany_shouldReturnCreatedCompany() throws Exception {
        CompanyRequestDto request = new CompanyRequestDto();
        request.companyName = "Google";
        request.industry = "Tech";
        request.description = "Search Engine";
        request.website = "https://google.com";
        request.contactEmail = "hr@google.com";

        CompanyResponseDto response = new CompanyResponseDto();
        response.id = 1L;
        response.companyName = "Google";

        when(companyService.createCompany(any()))
                .thenReturn(response);

        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Google"));
    }

    // ---------- GET BY ID ----------

    @Test
    void getCompanyById_shouldReturnCompany() throws Exception {
        CompanyResponseDto response = new CompanyResponseDto();
        response.id = 1L;
        response.companyName = "Google";

        when(companyService.getCompanyById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/companies/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    // ---------- GET ALL ----------

    @Test
    void getAllCompanies_shouldReturnList() throws Exception {
        CompanyResponseDto response = new CompanyResponseDto();
        response.id = 1L;
        response.companyName = "Google";

        when(companyService.getAllCompanies())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    // ---------- UPDATE ----------

    @Test
    void updateCompany_shouldReturnUpdatedCompany() throws Exception {
        CompanyRequestDto request = new CompanyRequestDto();
        request.companyName = "Updated Google";

        CompanyResponseDto response = new CompanyResponseDto();
        response.companyName = "Updated Google";

        when(companyService.updateCompany(eq(1L), any()))
                .thenReturn(response);

        mockMvc.perform(put("/companies/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName")
                        .value("Updated Google"));
    }

    // ---------- DELETE ----------

    @Test
    void deleteCompanyById_shouldReturnOk() throws Exception {
        doNothing().when(companyService).deleteCompanyById(1L);

        mockMvc.perform(delete("/companies/{id}", 1L))
                .andExpect(status().isOk());
    }
}
