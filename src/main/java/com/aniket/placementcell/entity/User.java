package com.aniket.placementcell.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String username; // Use email as username

    private String password;
    private String role;

    @OneToOne
    @JoinColumn(name = "crn_number", referencedColumnName = "crn_number")
    private Student student;
}
