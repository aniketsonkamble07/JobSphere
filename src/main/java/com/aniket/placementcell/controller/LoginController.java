package com.aniket.placementcell.controller;


import com.aniket.placementcell.dto.LoginRequest;
import com.aniket.placementcell.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*  This is rest Controller
@RestController
@RequestMapping("/pvg")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtUtil.generateToken(request.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("jwt", token);
            return response;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}


 */
@Controller
@RequestMapping("/pvg")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @GetMapping("/login")
    public String loginPage() {
        System.out.println("Rendering login page");
        return "login"; // login.html
    }

    @PostMapping("/login")
    public String loginChecker(@RequestParam(required = false) String username,
                               @RequestParam(required = false) String password,
                               @RequestParam String role,
                               Model model) {

        System.out.println("Login POST called");
        System.out.println("Received username: " + username);
        System.out.println("Received password: " + password);

        if (username == null || password == null) {
            System.out.println("Username or password is null!");
            model.addAttribute("error", "Username or password missing!");
            return "login";
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );


            if (authentication.isAuthenticated()) {
                String token = jwtUtil.generateToken(username);
                model.addAttribute("username", username);
                model.addAttribute("jwt", token);
                System.out.println("Login successful for: " + username);
                return "home"; // home.html
            } else {
                System.out.println("Authentication failed");
                model.addAttribute("error", "Invalid credentials");
                return "login";
            }
        } catch (Exception ex) {
            System.out.println("Exception during login: " + ex.getMessage());
            model.addAttribute("error", ex.getMessage());
            return "login";
        }
    }
}
