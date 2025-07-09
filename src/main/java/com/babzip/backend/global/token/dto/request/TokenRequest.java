package com.babzip.backend.global.token.dto.request;

public record TokenRequest (
    String accessToken,
    String refreshToken
){

}
