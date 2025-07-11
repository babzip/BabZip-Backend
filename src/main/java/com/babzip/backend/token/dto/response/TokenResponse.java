package com.babzip.backend.token.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {}
