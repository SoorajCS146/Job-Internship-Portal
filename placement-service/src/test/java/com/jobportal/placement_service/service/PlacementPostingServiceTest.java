package com.jobportal.placement_service.service;

import com.jobportal.placement_service.model.JobType;
import com.jobportal.placement_service.model.PlacementPosting;
import com.jobportal.placement_service.repository.PlacementPostingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlacementPostingServiceTest {

    @Mock
    private PlacementPostingRepository repo;

    @InjectMocks
    private PlacementPostingService service;

    private PlacementPosting posting;

    @BeforeEach
    void setUp() {
        posting = new PlacementPosting();
        posting.setId(1L);
        posting.setTitle("Software Engineer");
        posting.setCompanyName("ABC Corp");
        posting.setJobType(JobType.JOB);
        posting.setLocation("Bangalore");
        posting.setCtc(10.0);
    }

    // ---------------- CREATE ----------------
    @Test
    void createPosting_shouldSaveAndReturnPosting() {
        when(repo.save(posting)).thenReturn(posting);

        PlacementPosting result = service.createPosting(posting);

        assertNotNull(result);
        assertEquals("Software Engineer", result.getTitle());
        verify(repo, times(1)).save(posting);
    }

    // ---------------- GET ALL ----------------
    @Test
    void getAllPostings_shouldReturnList() {
        when(repo.findAll()).thenReturn(Arrays.asList(posting));

        List<PlacementPosting> result = service.getAllPostings();

        assertEquals(1, result.size());
        verify(repo, times(1)).findAll();
    }

    // ---------------- GET BY ID ----------------
    @Test
    void getPosting_shouldReturnPosting_whenIdExists() {
        when(repo.findById(1L)).thenReturn(Optional.of(posting));

        PlacementPosting result = service.getPosting(1L);

        assertNotNull(result);
        assertEquals("ABC Corp", result.getCompanyName());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void getPosting_shouldThrowException_whenIdNotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.getPosting(1L)
        );

        assertEquals("Posting not found", exception.getMessage());
    }

    // ---------------- UPDATE ----------------
    @Test
    void updatePosting_shouldUpdateAndReturnPosting() {
        PlacementPosting updated = new PlacementPosting();
        updated.setTitle("Senior Engineer");
        updated.setLocation("Hyderabad");

        when(repo.findById(1L)).thenReturn(Optional.of(posting));
        when(repo.save(any(PlacementPosting.class))).thenReturn(posting);

        PlacementPosting result = service.updatePosting(1L, updated);

        assertEquals("Senior Engineer", result.getTitle());
        assertEquals("Hyderabad", result.getLocation());
        verify(repo).save(posting);
    }

    // ---------------- DELETE ----------------
    @Test
    void deletePosting_shouldCallRepositoryDelete() {
        doNothing().when(repo).deleteById(1L);

        service.deletePosting(1L);

        verify(repo, times(1)).deleteById(1L);
    }
}
