package com.aniket.placementcell.controller;

import com.aniket.placementcell.dto.JobPostingResponseDTO;
import com.aniket.placementcell.dto.JobValidationResponse;
import com.aniket.placementcell.dto.StudentRequestDto;
import com.aniket.placementcell.dto.StudentResponseDTO;
import com.aniket.placementcell.entity.Student;
import com.aniket.placementcell.entity.User;
import com.aniket.placementcell.repository.StudentRepository;
import com.aniket.placementcell.repository.UserRepository;
import com.aniket.placementcell.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pvg/student")
public class StudentHomeController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/home")
    public String studentHomePage(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/pvg/login";
        }

        String user = authentication.getName();
        StudentResponseDTO student = studentService.sendProfile(user);
        List<JobPostingResponseDTO> jobs = studentService.getAllJobPosting();

        model.addAttribute("student", student);
        model.addAttribute("jobs", jobs); // Changed from "job" to "jobs" for clarity

        return "student_home";
    }

    @GetMapping("/job/details/{jobId}")
    public String showJobDetails(@PathVariable String jobId, Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/pvg/login";
        }

        JobPostingResponseDTO job = studentService.getJobPosting(jobId);
        model.addAttribute("job", job);

        return "job_details";
    }

    @GetMapping("/job/applyJob/{jobId}")
    public String applyForJob(@PathVariable String jobId,Model model, Authentication authentication)
    {
        if(authentication ==null ||!authentication.isAuthenticated())
        {
            return "redirect:/pvg/login";
        }
        String username=authentication.getName();
        JobValidationResponse response = studentService.checkCredentialsOfJobId(jobId, username);

        return "student_home";
    }


    @GetMapping("/update-profile/{username}")
    public String showUpdateProfilePage(@PathVariable String username, Model model) {
        System.out.println("[DEBUG] GET /pvg/student/update-profile/" + username);

        // Fetch existing student
        StudentResponseDTO student = studentService.getStudentByEmail(username);

        // Map entity to DTO for form
        StudentRequestDto dto = StudentRequestDto.builder()
                .name(student.getName())
                .email(student.getEmail())
                .branch(student.getBranch())
                .year(student.getYear())
                .passingYear(student.getPassingYear())
                .mobileNumber(student.getMobileNumber())
                .cgpa(student.getCgpa())
                .mark10th(student.getMark10th())
                .mark12th(student.getMark12th())
                .diplomaMarks(student.getDiplomaMarks())
                .aggregateMarks(student.getAggregateMarks())
                .yearDown(student.getYearDown())
                .activeBacklog(student.getActiveBacklog())
                .gender(student.getGender())
                .build();

        model.addAttribute("studentRequestDTO", dto);
        return "update-profile"; // Thymeleaf page: update-profile.html
    }

    // 2️⃣ Handle profile update form submission
    @PostMapping("/update-profile/{username}")
    public String updateProfile(@PathVariable String username,
                                @Valid @ModelAttribute StudentRequestDto dto,
                                BindingResult result,
                                Model model) {

        if(result.hasErrors()) {
            System.out.println("[DEBUG] Validation errors while updating student profile: " + result.getAllErrors());
            return "update-profile";
        }

        studentService.updateStudentProfile(username, dto);

        return "redirect:/pvg/student/home"; // redirect after successful update
    }
}