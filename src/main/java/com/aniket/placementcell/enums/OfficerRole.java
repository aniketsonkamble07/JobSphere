package com.aniket.placementcell.enums;

public enum OfficerRole {
    HEAD_PLACEMENT("Head Placement Officer"),
    PLACEMENT_OFFICER("Placement Officer"),
    INDUSTRY_RELATIONS_MANAGER("Industry Relations Manager"),
    STUDENT_COORDINATOR("Student Coordinator"),
    JOB_COORDINATOR("Job Coordinator"),
    TRAINING_COORDINATOR("Training Coordinator"),
    DATA_ANALYST("Data Analyst"),
    SUPPORT_STAFF("Support Staff");

    private final String displayName;

    OfficerRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
