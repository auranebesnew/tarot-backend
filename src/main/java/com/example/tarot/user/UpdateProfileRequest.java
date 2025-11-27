package com.example.tarot.user;

import jakarta.validation.constraints.Size;

public class UpdateProfileRequest {

    @Size(min = 2, max = 64, message = "Display name must be 2-64 characters")
    private String displayName;

    public UpdateProfileRequest() {
    }

    public UpdateProfileRequest(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
