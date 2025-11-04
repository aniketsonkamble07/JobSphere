package com.aniket.placementcell.controller;

import com.aniket.placementcell.dto.JobPostingRequestDTO;
import com.aniket.placementcell.dto.StudentRequestDto;
import com.aniket.placementcell.service.RegisterStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/*
@RestController
@RequestMapping("/pvg")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterStudentService service; // No @Autowired needed with RequiredArgsConstructor

    @GetMapping("/register")
    public String registerStudent() {
        System.out.println("[DEBUG] GET /pvg/register called");
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody StudentRequestDto student) {
        System.out.println("[DEBUG] POST /pvg/register called");
        System.out.println("[DEBUG] Received student data: " + student);

        service.registerStudent(student);

        System.out.println("[DEBUG] Student registration completed for: " + student.getName());
        return "Student registered successfully";
    }
}
*/


@RequiredArgsConstructor
@Controller
@RequestMapping("/pvg/student/")
public class RegisterController
{
    private final RegisterStudentService service; // No @Autowired needed with RequiredArgsConstructor

    @GetMapping("/register")
public String registerStudentPage(Model model) {
    System.out.println("[DEBUG] GET /pvg/register called");
        model.addAttribute("studentRequestDTO", new StudentRequestDto());


        return "register"; // Thymeleaf will render register.html
}

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute StudentRequestDto dto, BindingResult result) {
        if(result.hasErrors()) return "register";
        service.registerStudent(dto);
        return "redirect:/pvg/student/home";
    }


        }

