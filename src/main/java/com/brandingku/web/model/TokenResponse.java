package com.brandingku.web.model;

public record TokenResponse(
        Boolean success,
        String message,
        String token
) {
}
