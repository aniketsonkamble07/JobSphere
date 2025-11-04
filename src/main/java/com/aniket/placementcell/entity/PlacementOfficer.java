package com.aniket.placementcell.entity;

import com.aniket.placementcell.enums.OfficerRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlacementOfficer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Full name is required")
    @Size(max = 100, message = "Job title must not exceed 200 characters")
    @Column(nullable=false, length=100)
    String  name;

    @NotBlank(message="Position is required")
    @Size(max=100, message="Position must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    String position;

    @NotBlank(message ="Email is required")
    @Column(nullable = false,length = 30,unique = true)
    String email;


    @NotBlank(message = "Mobile numbers is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    @Column(length = 10)
    String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OfficerRole role;

@Column(length = 500)
private String photoUrl;

@Column(nullable = false)
    private  boolean isActive;

private LocalDateTime createdAt;


private LocalDateTime updatedAt;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    public boolean canManageCompanies() {
        return role == OfficerRole.PLACEMENT_OFFICER ||
                role == OfficerRole.HEAD_PLACEMENT ||
                role == OfficerRole.INDUSTRY_RELATIONS_MANAGER;
    }

    public boolean canPostJobs() {
        return role == OfficerRole.PLACEMENT_OFFICER ||
                role == OfficerRole.HEAD_PLACEMENT ||
                role == OfficerRole.JOB_COORDINATOR;
    }

    public boolean canManageStudents() {
        return role == OfficerRole.PLACEMENT_OFFICER ||
                role == OfficerRole.HEAD_PLACEMENT ||
                role == OfficerRole.STUDENT_COORDINATOR;
    }
    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
}
