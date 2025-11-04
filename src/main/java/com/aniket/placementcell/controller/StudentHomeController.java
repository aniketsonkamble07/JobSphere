package com.aniket.placementcell.controller;

import com.aniket.placementcell.dto.JobPostingResponseDTO;
import com.aniket.placementcell.dto.StudentResponseDTO;
import com.aniket.placementcell.entity.Student;
import com.aniket.placementcell.entity.User;
import com.aniket.placementcell.repository.StudentRepository;
import com.aniket.placementcell.repository.UserRepository;
import com.aniket.placementcell.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pvg/student")
public class StudentHomeController {

    @Autowired
    private StudentService service;

    @GetMapping("/home")
    public String studentHomePage(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/pvg/login";
        }

        String user = authentication.getName();
        StudentResponseDTO student = service.sendProfile(user);
        List<JobPostingResponseDTO> jobs = service.getAllJobPosting();

        model.addAttribute("student", student);
        model.addAttribute("jobs", jobs); // Changed from "job" to "jobs" for clarity

        return "student_home";
    }

    @GetMapping("/job/details/{jobId}")
    public String showJobDetails(@PathVariable String jobId, Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/pvg/login";
        }

        JobPostingResponseDTO job = service.getJobPosting(jobId);
        model.addAttribute("job", job);

        return "job_details";
    }
}