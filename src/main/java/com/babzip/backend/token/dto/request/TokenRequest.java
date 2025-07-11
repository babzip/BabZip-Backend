package com.babzip.backend.token.dto.request;

public record TokenRequest (
    String accessToken,
    String refreshToken
){

}
