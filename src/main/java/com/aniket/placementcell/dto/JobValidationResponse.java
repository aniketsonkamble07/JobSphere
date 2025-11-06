package com.aniket.placementcell.dto;

public class JobValidationResponse {
    private boolean eligible;
    private String message;

    public JobValidationResponse(boolean eligible, String message) {
        this.eligible = eligible;
        this.message = message;
    }

    public boolean isEligible() {
        return eligible;
    }

    public String getMessage() {
        return message;
    }
}
