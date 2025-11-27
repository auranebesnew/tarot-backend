package com.example.tarot.auth;

import jakarta.validation.constraints.NotBlank;

public class RefreshRequest {

    @NotBlank
    private String refreshToken;

    public RefreshRequest() {
    }

    public RefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
