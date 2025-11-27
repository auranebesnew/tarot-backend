package com.example.tarot.common;

import java.time.Instant;

public class ApiError {
    private Instant timestamp = Instant.now();
    private int status;
    private String error;
    private String path;

    public ApiError(int status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
