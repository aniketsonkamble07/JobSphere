package com.aniket.placementcell.controller;

import com.aniket.placementcell.dto.PlacementOfficerRequestDTO;
import com.aniket.placementcell.service.PlacementOfficerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/*
@RestController
@RequestMapping("/officer")
public class PlacementOfficerController {

    private final PlacementOfficerService service;
    public PlacementOfficerController(PlacementOfficerService service) {
        this.service = service;
    }
    @GetMapping("/registerOfficer")
    public String register()
    {
        return "placementofficeradd";
    }

  @PostMapping("/registerOfficer")
  public String registerOfficer(@Valid @RequestBody PlacementOfficerRequestDTO dto)
  {
      service.registerOfficer(dto);
      String message= "Successfully registered placement officer"+ dto.getName();
      return message;
  }
}

*/

@Controller
@RequestMapping("/officer")
public class PlacementOfficerController {

    private final PlacementOfficerService service;
    public PlacementOfficerController(PlacementOfficerService service) {
        this.service = service;
    }
    @GetMapping("/registerOfficer")
    public String register()
    {
        return "placementofficeradd";
    }

    @PostMapping("/registerOfficer")
    public String registerOfficer(@Valid @ModelAttribute PlacementOfficerRequestDTO dto, Model model)
    {

        service.registerOfficer(dto);
        model.addAttribute("message", "Successfully registered placement officer " + dto.getName());


        return "placementofficeradd";
    }
}

