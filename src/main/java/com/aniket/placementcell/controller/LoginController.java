package com.aniket.placementcell.controller;


import com.aniket.placementcell.dto.LoginRequest;
import com.aniket.placementcell.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
@RestController
@RequestMapping("/pvg")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequest request) {
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

    // Render login page
    @GetMapping("/login")
    public String loginPage(Model model) {
        System.out.println("Rendering login page");
        model.addAttribute("loginRequest", new LoginRequest());
        return "login"; // login.html
    }

    // Handle login POST
    @PostMapping("/login")
    public String loginRequest(@Valid @ModelAttribute LoginRequest loginRequest,
                               Model model,
                               HttpServletResponse response) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            String token = jwtUtil.generateToken(loginRequest.getUsername());

            // More secure cookie configuration
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true); // Only over HTTPS in production
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60);
            // Consider SameSite attribute
            response.addCookie(jwtCookie);

            // Redirect instead of returning view names
            return determineRedirectUrl(authentication);

        } catch (AuthenticationException ex) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    private String determineRedirectUrl(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> {
                    String authority = grantedAuthority.getAuthority();
                    switch (authority) {
                        case "ROLE_ADMIN":
                            return "redirect:dashboard";
                        case "ROLE_PLACEMENT_OFFICER":
                            return "redirect:/jobs/add";
                        case "ROLE_STUDENT":
                            return "redirect:/pvg/student/home";
                        default:
                            return "redirect:jobs";
                    }
                })
                .orElse("redirect:/user/jobs");
    }
}