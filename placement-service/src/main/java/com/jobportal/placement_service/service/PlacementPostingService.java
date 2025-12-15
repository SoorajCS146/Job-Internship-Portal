package com.jobportal.placement_service.service;

import com.jobportal.placement_service.client.CompanyClient;
import com.jobportal.placement_service.client.StudentClient;
import com.jobportal.placement_service.dto.CompanyDTO;
import com.jobportal.placement_service.dto.StudentDTO;
import com.jobportal.placement_service.model.PlacementPosting;
import com.jobportal.placement_service.repository.PlacementPostingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlacementPostingService {

    private final PlacementPostingRepository repo;
    private final StudentClient studentClient;
    private final CompanyClient companyClient;

    public PlacementPostingService(PlacementPostingRepository repo, StudentClient studentClient, CompanyClient companyClient) {
        this.repo = repo;
        this.studentClient = studentClient;
        this.companyClient = companyClient;
    }

    public PlacementPosting createPosting(PlacementPosting posting) {
        return repo.save(posting);
    }

    public List<PlacementPosting> getAllPostings() {
        return repo.findAll();
    }

    public PlacementPosting getPosting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Posting not found"));
    }

    public PlacementPosting updatePosting(Long id, PlacementPosting updated) {
        PlacementPosting existing = getPosting(id);

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCompanyName(updated.getCompanyName());
        existing.setJobType(updated.getJobType());
        existing.setLocation(updated.getLocation());
        existing.setCtc(updated.getCtc());
        existing.setEligibleBranches(updated.getEligibleBranches());
        existing.setMinimumCgpa(updated.getMinimumCgpa());
        existing.setApplicationDeadline(updated.getApplicationDeadline());

        return repo.save(existing);
    }

    public void deletePosting(Long id) {
        repo.deleteById(id);
    }

    // Student: Feign-Client call
    public boolean isStudentEligible(String usn, Long postingId) {
        StudentDTO student = studentClient.getStudentByUsn(usn);
        PlacementPosting posting = getPosting(postingId);

        // Check CGPA
        if (student.getCgpa() < posting.getMinimumCgpa()) return false;

        // Check branch
        String branches = posting.getEligibleBranches(); // CSV string
        if (!branches.contains(student.getBranch())) return false;

        return true;
    }

    // Company: Feign-Client call
    public List<PlacementPosting> getPostingsByCompany(Long companyId) {
        CompanyDTO company;
        company = companyClient.getCompanyById(companyId);

        if (company == null) {
            throw new RuntimeException("Company not found with ID: " + companyId);
        }

        return repo.findByCompanyName(company.getCompanyName());
    }
}

