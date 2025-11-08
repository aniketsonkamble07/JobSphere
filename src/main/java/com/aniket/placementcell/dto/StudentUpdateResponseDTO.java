package com.aniket.placementcell.dto;

import com.aniket.placementcell.enums.Branch;
import com.aniket.placementcell.enums.Gender;
import com.aniket.placementcell.enums.PlacementStatus;
import com.aniket.placementcell.enums.StudentYear;
import lombok.Data;

@Data
public class StudentUpdateResponseDTO {
    private  String name;
    private Long crnNumber;
    private String email;
    private Branch branch;
    private StudentYear studentYear;
    private String mobileNumber;
    private Double cgpa;
    private Double mark10th;
    private Double mark12th;
    private Double diplomaMarks;
    private Double aggregateMarks;
    private int activeBacklog;
    private boolean yearDown;
    private PlacementStatus placementStatus;

    private Gender gender;
    private String companyName;
    private  Double salary;
    private String remarks;
    private Integer passingYear;
    private StudentYear year;
}
