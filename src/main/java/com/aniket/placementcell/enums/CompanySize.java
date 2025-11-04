package com.aniket.placementcell.enums;

public enum CompanySize {
    STARTUP("1-50 employees"),
    SMALL("51-200 employees"),
    MEDIUM("201-500 employees"),
    LARGE("501-1000 employees"),
    ENTERPRISE("1000+ employees");

    private final String displayName;

    CompanySize(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
