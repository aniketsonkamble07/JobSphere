package com.aniket.placementcell.controller;

import com.aniket.placementcell.dto.JobPostingRequestDTO;
import com.aniket.placementcell.entity.PlacementOfficer;
import com.aniket.placementcell.repository.PlacementOfficerRepository;
import com.aniket.placementcell.service.JobPostingService;
import com.aniket.placementcell.service.PlacementOfficerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/*
@RestController
@RequestMapping("/jobs")
public class JobPostingController {

    @Autowired
    private JobPostingService service;

    @Autowired
    private PlacementOfficerRepository officerRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addJobPost(@RequestBody JobPostingRequestDTO dto, Authentication authentication) {
        // Get officer from JWT authentication
        String username = authentication.getName();
        PlacementOfficer officer = officerRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Officer not found: " + username));

        service.addJobPost(dto, officer);

        return ResponseEntity.ok("Job added successfully! Job ID: " );
    }
}
*/

@Controller
@RequestMapping("/jobs")
public class JobPostingController {

    @Autowired
    private JobPostingService service;

    @Autowired
    private PlacementOfficerRepository officerRepository;

    @GetMapping("/add")
    public String showJobForm(Model model) {
        model.addAttribute("jobPostingRequestDTO", new JobPostingRequestDTO());
        return "addjob";
    }

    @PostMapping("/add")
    public String addJobPost(@Valid @ModelAttribute JobPostingRequestDTO dto,
                             Authentication authentication,
                             Model model) {

        String username = authentication.getName();
        PlacementOfficer officer = officerRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Officer not found: " + username));

        service.addJobPost(dto, officer);

        model.addAttribute("success", "Job added successfully!");
        return "addjob"; // or redirect:/jobs/add if you want to refresh
    }

}



