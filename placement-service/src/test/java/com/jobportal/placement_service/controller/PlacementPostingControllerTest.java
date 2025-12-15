package com.jobportal.placement_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.placement_service.model.JobType;
import com.jobportal.placement_service.model.PlacementPosting;
import com.jobportal.placement_service.service.PlacementPostingService;
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

@WebMvcTest(PlacementPostingController.class)
class PlacementPostingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlacementPostingService service;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------------- CREATE ----------------
    @Test
    void createPlacement_shouldReturnCreatedPosting() throws Exception {
        PlacementPosting posting = new PlacementPosting();
        posting.setId(1L);
        posting.setTitle("Software Engineer");
        posting.setCompanyName("ABC Corp");
        posting.setJobType(JobType.JOB);

        when(service.createPosting(any(PlacementPosting.class)))
                .thenReturn(posting);

        mockMvc.perform(post("/placements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(posting)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Software Engineer"))
                .andExpect(jsonPath("$.jobType").value("JOB"));
    }

    // ---------------- GET ALL ----------------
    @Test
    void getAllPlacements_shouldReturnList() throws Exception {
        PlacementPosting posting = new PlacementPosting();
        posting.setId(1L);
        posting.setTitle("SE");

        when(service.getAllPostings()).thenReturn(List.of(posting));

        mockMvc.perform(get("/placements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("SE"));
    }

    // ---------------- GET BY ID ----------------
    @Test
    void getPlacementById_shouldReturnPosting() throws Exception {
        PlacementPosting posting = new PlacementPosting();
        posting.setId(1L);
        posting.setTitle("Backend Engineer");

        when(service.getPosting(1L)).thenReturn(posting);

        mockMvc.perform(get("/placements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Backend Engineer"));
    }

    // ---------------- UPDATE ----------------
    @Test
    void updatePlacement_shouldReturnUpdatedPosting() throws Exception {
        PlacementPosting updated = new PlacementPosting();
        updated.setTitle("Senior Engineer");

        when(service.updatePosting(eq(1L), any(PlacementPosting.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/placements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Senior Engineer"));
    }

    // ---------------- DELETE ----------------
    @Test
    void deletePlacement_shouldReturnOk() throws Exception {
        doNothing().when(service).deletePosting(1L);

        mockMvc.perform(delete("/placements/1"))
                .andExpect(status().isOk());

        verify(service).deletePosting(1L);
    }
}
