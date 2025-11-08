package com.aniket.placementcell.service;

import com.aniket.placementcell.dto.StudentRequestDTO;
import com.aniket.placementcell.entity.Student;
import com.aniket.placementcell.entity.User;
import com.aniket.placementcell.exceptions.AlreadyPresentException;
import com.aniket.placementcell.repository.StudentRepository;
import com.aniket.placementcell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterStudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerStudent(StudentRequestDTO dto) {
        System.out.println("[DEBUG] Starting registration for: " + dto.getEmail());

        validateStudent(dto);

        // 1️⃣ Create User for authentication
        User user = new User();
        user.setUsername(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("STUDENT");
        userRepository.save(user);
        System.out.println("[DEBUG] User account created for: " + user.getUsername());

        // 2️⃣ Create Student entity linked to user
        Student s = new Student();
        s.setCrnNumber(dto.getCrnNumber());
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        s.setBranch(dto.getBranch());
        s.setYear(dto.getYear());
        s.setPassingYear(dto.getPassingYear());
        s.setMobileNumber(dto.getMobileNumber());
        s.setPlacementStatus(dto.getPlacementStatus());
        s.setGender(dto.getGender());
        s.setCgpa(dto.getCgpa());
        s.setMark10th(dto.getMark10th());
        s.setMark12th(dto.getMark12th());
        s.setDiplomaMarks(dto.getDiplomaMarks());
        s.setAggregateMarks(dto.getAggregateMarks());
        s.setYearDown(dto.getYearDown());
        s.setActiveBacklog(dto.getActiveBacklog());
        s.setRemarks(dto.getRemarks());
        s.setCompanyName(dto.getCompanyName());
        s.setSalary(dto.getSalary());

        s.setUser(user); // link to user
        studentRepository.save(s);

        System.out.println("[DEBUG] Student saved with CRN: " + s.getCrnNumber());
    }

    private void validateStudent(StudentRequestDTO dto) {
        if (studentRepository.existsByCrnNumber(dto.getCrnNumber())) {
            throw new AlreadyPresentException("CRN Number " + dto.getCrnNumber() + " already present!!");
        }

        if (studentRepository.existsByEmail(dto.getEmail()) || userRepository.existsByUsername(dto.getEmail())) {
            throw new AlreadyPresentException("Email Id " + dto.getEmail() + " already present!!");
        }

        System.out.println("[DEBUG] Validation passed for: " + dto.getEmail());
    }
}
