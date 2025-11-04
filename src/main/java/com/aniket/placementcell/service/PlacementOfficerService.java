package com.aniket.placementcell.service;

import com.aniket.placementcell.dto.PlacementOfficerRequestDTO;
import com.aniket.placementcell.entity.PlacementOfficer;
import com.aniket.placementcell.entity.User;
import com.aniket.placementcell.exceptions.AlreadyPresentException;
import com.aniket.placementcell.repository.PlacementOfficerRepository;
import com.aniket.placementcell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlacementOfficerService {

    private final PlacementOfficerRepository placementOfficerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerOfficer(PlacementOfficerRequestDTO dto) {
        System.out.println("[DEBUG] Starting registration for officer: " + dto.getEmail());

        validateOfficer(dto);


        User user = new User();
        user.setUsername(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole().toString());
        userRepository.save(user);
        System.out.println("[DEBUG] User account created for officer: " + user.getUsername());


        PlacementOfficer officer = new PlacementOfficer();
        officer.setName(dto.getName());
        officer.setEmail(dto.getEmail());
        officer.setPosition(dto.getPosition());
        officer.setMobileNumber(dto.getMobileNumber());
        officer.setPhotoUrl(dto.getPhotoUrl());
        officer.setRole(dto.getRole());
        officer.setActive(dto.isActive());

        officer.setUser(user); // Link to User
        placementOfficerRepository.save(officer);

        System.out.println("[DEBUG] PlacementOfficer saved: " + officer.getName());
    }

    private void validateOfficer(PlacementOfficerRequestDTO dto) {
        // Check if email already exists in officer table
        if (placementOfficerRepository.existsByEmail(dto.getEmail())) {
            throw new AlreadyPresentException("Placement officer with email " + dto.getEmail() + " already exists!");
        }

        // Check if username already exists in user table
        if (userRepository.existsByUsername(dto.getEmail())) {
            throw new AlreadyPresentException("User with email " + dto.getEmail() + " already exists!");
        }

        System.out.println("[DEBUG] Validation passed for officer: " + dto.getEmail());
    }
}
