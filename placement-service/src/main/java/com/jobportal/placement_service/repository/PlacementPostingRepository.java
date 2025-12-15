package com.jobportal.placement_service.repository;

import com.jobportal.placement_service.model.PlacementPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlacementPostingRepository extends JpaRepository<PlacementPosting, Long> {
    List<PlacementPosting> findByCompanyId(Long companyId);
}
