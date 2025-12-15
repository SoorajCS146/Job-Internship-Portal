package com.jobportal.placement_service.controller;

import com.jobportal.placement_service.model.PlacementPosting;
import com.jobportal.placement_service.service.PlacementPostingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/placements")
public class PlacementPostingController {

    private final PlacementPostingService service;

    public PlacementPostingController(PlacementPostingService service) {
        this.service = service;
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
}

