package com.aniket.placementcell.dto;

import com.aniket.placementcell.enums.OfficerRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlacementOfficerResponseDTO {

    private String name;
    private String email;
    private String position;
    private String mobileNumber;
    private String photoUrl;
    private OfficerRole role;
    private boolean active;
}
