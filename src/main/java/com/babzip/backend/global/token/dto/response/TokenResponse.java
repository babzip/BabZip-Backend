package com.babzip.backend.global.token.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {}
