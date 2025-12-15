package com.jobportal.placement_service.controller;

import com.jobportal.placement_service.client.CompanyClient;
import com.jobportal.placement_service.client.StudentClient;
import com.jobportal.placement_service.dto.CompanyDTO;
import com.jobportal.placement_service.dto.PlacementEligibilityDTO;
import com.jobportal.placement_service.dto.StudentDTO;
import com.jobportal.placement_service.model.PlacementPosting;
import com.jobportal.placement_service.service.PlacementPostingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/placements")
public class PlacementPostingController {

    private final PlacementPostingService service;
    private final StudentClient studentClient;
    private final CompanyClient companyClient;

    public PlacementPostingController(PlacementPostingService service, StudentClient studentClient, CompanyClient companyClient) {
        this.service = service;
        this.studentClient = studentClient;
        this.companyClient = companyClient;
    }

    @PostMapping
    public PlacementPosting create(@RequestBody PlacementPosting posting) {
        return service.createPosting(posting);
    }

    @GetMapping
    public List<PlacementPosting> getAll() {
        return service.getAllPostings();
    }

    @GetMapping("/{id}")
    public PlacementPosting getOne(@PathVariable Long id) {
        return service.getPosting(id);
    }

    @PutMapping("/{id}")
    public PlacementPosting update(@PathVariable Long id, @RequestBody PlacementPosting posting) {
        return service.updatePosting(id, posting);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deletePosting(id);
    }

    @GetMapping("/check-eligibility/{usn}/{postingId}")
    public boolean checkEligibility(@PathVariable String usn, @PathVariable Long postingId) {
        return service.isStudentEligible(usn, postingId);
    }

    @GetMapping("/company/{companyId}")
    public List<PlacementPosting> getPostingsByCompany(@PathVariable Long companyId) {
        return service.getPostingsByCompany(companyId);
    }

    // GET combined info for eligibility
    @GetMapping("/eligibility/student/{studentId}/company/{companyId}")
    public PlacementEligibilityDTO getPlacementEligibility(
            @PathVariable Long studentId,
            @PathVariable Long companyId) {

        // Fetch student info
        StudentDTO student = studentClient.getStudentById(studentId);

        // Fetch company info
        CompanyDTO company = companyClient.getCompanyById(companyId);

        return new PlacementEligibilityDTO(student, company);
    }

}

// Functionalities:
// END-POINT: "/placements"
// 1. Create a new placement posting        -> POST /  {RqstBody}
// 2. Get all placement postings           -> GET /
// 3. Get a specific placement posting      -> GET /{id}
// 4. Update a specific placement posting   -> PUT /{id} {RqstBody}
// 5. Delete a specific placement posting   -> DELETE /{id}

// 6. Check if a student is eligible for a specific placement posting -> GET /check-eligibility/{usn}/{postingId}
// 7. Get all placement postings for a specific company              -> GET /company/{companyId}

// 8. Get combined info for eligibility                             -> GET /eligibility/student/{studentId}/company/{companyId}


