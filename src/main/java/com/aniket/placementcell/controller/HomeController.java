package com.aniket.placementcell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pvg")
public class HomeController {

    @GetMapping("/home")
    public String StudentHomePage()
    {
        return "home";
    }
}
