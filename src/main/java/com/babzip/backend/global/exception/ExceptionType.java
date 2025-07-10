package com.babzip.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    // Common
    UNEXPECTED_SERVER_ERROR(INTERNAL_SERVER_ERROR,"C001","예상치 못한 에러 발생"),
    BINDING_ERROR(BAD_REQUEST,"C002","바인딩시 에러 발생"),
    ESSENTIAL_FIELD_MISSING_ERROR(NO_CONTENT , "C003","필수적인 필드 부재"),

    // Security
    ILLEGAL_REGISTRATION_ID(NOT_ACCEPTABLE, "S001", "잘못된 registration id 입니다"),
    NEED_AUTHORIZED(UNAUTHORIZED, "S002", "인증이 필요합니다."),
    ACCESS_DENIED(FORBIDDEN, "S003", "권한이 없습니다."),
    JWT_EXPIRED(UNAUTHORIZED, "S004", "JWT 토큰이 만료되었습니다."),
    JWT_INVALID(UNAUTHORIZED, "S005", "JWT 토큰이 올바르지 않습니다."),
    JWT_NOT_EXIST(UNAUTHORIZED, "S006", "JWT 토큰이 존재하지 않습니다."),

    // Token
    REFRESH_TOKEN_NOT_EXIST(NOT_FOUND, "T001", "리프래시 토큰이 존재하지 않습니다"),
    TOKEN_NOT_MATCHED(UNAUTHORIZED, "T002","일치하지 않는 토큰입니다"),


    // User
    USER_NOT_FOUND(NOT_FOUND, "U001","사용자가 존재하지 않습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
