package com.aniket.placementcell.enums;

public enum AnnouncementType {
    GENERAL("General"),
    JOB_ALERT("Job Alert"),
    TRAINING("Training"),
    DEADLINE("Deadline Reminder"),
    INTERVIEW("Interview Schedule"),
    RESULT("Result Announcement"),
    URGENT("Urgent"),
    MAINTENANCE("System Maintenance");

    private final String displayName;

    AnnouncementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
