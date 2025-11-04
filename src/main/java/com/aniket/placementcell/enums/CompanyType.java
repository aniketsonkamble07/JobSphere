package com.aniket.placementcell.enums;

public enum CompanyType {
    DREAM_COMPANY("Dream Company"),
    MASS_RECRUITER("Mass Recruiter"),
    STARTUP("Startup"),
    PRODUCT_BASED("Product Based"),
    SERVICE_BASED("Service Based"),
    MNC("Multinational Corporation"),
    GOVERNMENT("Government"),
    PSUS("Public Sector Undertaking");

    private final String displayName;

    CompanyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
