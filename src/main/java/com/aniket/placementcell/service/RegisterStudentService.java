package com.aniket.placementcell.service;

import com.aniket.placementcell.dto.StudentRequestDto;
import com.aniket.placementcell.entity.Student;
import com.aniket.placementcell.entity.User;
import com.aniket.placementcell.repository.StudentRepository;
import com.aniket.placementcell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerStudent(StudentRequestDto studentRequestDto) {
        System.out.println("[DEBUG] Starting registration for: " + studentRequestDto.getEmail());

        ValidateStudent(studentRequestDto);

        Student s = new Student();
        s.setCrnNumber(studentRequestDto.getCrnNumber());
        s.setName(studentRequestDto.getName());
        s.setEmail(studentRequestDto.getEmail());
        String encodedPass = passwordEncoder.encode(studentRequestDto.getPassword());
        s.setPassword(encodedPass);
        s.setBranch(studentRequestDto.getBranch());
        s.setYear(studentRequestDto.getYear());
        s.setPassingYear(studentRequestDto.getPassingYear());
        s.setMobileNumber(studentRequestDto.getMobileNumber());
        s.setPlacementStatus(studentRequestDto.getPlacementStatus());
        s.setGender(studentRequestDto.getGender());
        s.setCgpa(studentRequestDto.getCgpa());
        s.setMark10th(studentRequestDto.getMark10th());
        s.setMark12th(studentRequestDto.getMark12th());
        s.setDiplomaMarks(studentRequestDto.getDiplomaMarks());
        s.setAggregateMarks(studentRequestDto.getAggregateMarks());
        s.setYearDown(studentRequestDto.getYearDown());
        s.setActiveBacklog(studentRequestDto.getActiveBacklog());
        s.setRemarks(studentRequestDto.getRemarks());
        s.setCompanyName(studentRequestDto.getCompanyName());
        s.setSalary(studentRequestDto.getSalary());

        studentRepository.save(s);
        System.out.println("[DEBUG] Student saved with CRN: " + s.getCrnNumber());

        User user = new User();
        user.setUsername(studentRequestDto.getEmail());
        user.setPassword(encodedPass);
        user.setRole("STUDENT");
        user.setStudent(s);
        userRepository.save(user);
        System.out.println("[DEBUG] User account created for: " + user.getUsername());
    }

    public void ValidateStudent(StudentRequestDto dto) {
        if (studentRepository.existsByCrnNumber(dto.getCrnNumber())) {
            System.out.println("[DEBUG] CRN already exists: " + dto.getCrnNumber());
            throw new ArithmeticException("CRN Number " + dto.getCrnNumber() + " already present!!");
        }

        if (studentRepository.existsByEmail(dto.getEmail())) {
            System.out.println("[DEBUG] Email already exists: " + dto.getEmail());
            throw new ArithmeticException("Email Id " + dto.getEmail() + " already present!!");
        }

        System.out.println("[DEBUG] Validation passed for: " + dto.getEmail());
    }
}
