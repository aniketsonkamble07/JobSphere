package com.aniket.placementcell.dto;

import com.aniket.placementcell.enums.Branch;
import com.aniket.placementcell.enums.Gender;
import com.aniket.placementcell.enums.PlacementStatus;
import com.aniket.placementcell.enums.StudentYear;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentUpdateRequestDTO {
    @NotNull(message = "CRN number is required")
    private Long crnNumber;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;



    private String password;

    @NotNull(message = "Branch is required")
    private Branch branch;

    @NotNull(message = "Year is required")
    private StudentYear year;

    @NotNull(message = "Passing year is required")
    @Min(value = 2000, message = "Passing year must be valid")
    private Integer passingYear;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "\\d{10,15}", message = "Mobile number must be 10 to 15 digits")
    private String mobileNumber;

    @DecimalMin(value = "0.0", message = "CGPA must be at least 0")
    @DecimalMax(value = "10.0", message = "CGPA cannot exceed 10")
    private Double cgpa;

    @DecimalMin(value = "0.0", message = "10th marks must be at least 0")
    @DecimalMax(value = "100.0", message = "10th marks cannot exceed 100")
    private Double mark10th;

    @DecimalMin(value = "0.0", message = "12th marks must be at least 0")
    @DecimalMax(value = "100.0", message = "12th marks cannot exceed 100")
    private Double mark12th;

    @DecimalMin(value = "0.0", message = "Diploma marks must be at least 0")
    @DecimalMax(value = "100.0", message = "Diploma marks cannot exceed 100")
    private Double diplomaMarks;

    @DecimalMin(value = "0.0", message = "Aggregate marks must be at least 0")
    @DecimalMax(value = "100.0", message = "Aggregate marks cannot exceed 100")
    private Double aggregateMarks;

    private Boolean yearDown;

    @Min(value = 0, message = "Active backlog cannot be negative")
    private Integer activeBacklog;

    @NotNull(message = "Placement status is required")
    private PlacementStatus placementStatus;

    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    private String remarks;

    private Gender gender;

    private String companyName;

    private Double salary;
}
