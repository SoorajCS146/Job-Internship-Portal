package com.jobportal.placement_service.repository;

import com.jobportal.placement_service.model.PlacementPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementPostingRepository extends JpaRepository<PlacementPosting, Long> {
}
