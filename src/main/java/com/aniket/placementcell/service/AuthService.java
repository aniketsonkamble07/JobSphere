package com.aniket.placementcell.service;

import com.aniket.placementcell.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/*
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (!authentication.isAuthenticated()) {
                throw new BadCredentialsException("Invalid credentials");
            }

            // Extract role (remove "ROLE_" prefix)
            String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

            // Role-based redirect
            switch (role) {
                case "STUDENT":
                    return "/student/dashboard";
                case "TPO":
                    return "/tpo/dashboard";
                case "HR":
                    return "/hr/dashboard";
                default:
                    throw new BadCredentialsException("Unknown role");
            }

        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Authentication failed: " + ex.getMessage());
        }
    }

    public String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }
}
