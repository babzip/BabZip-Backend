package com.babzip.backend.global.jwt.exception;

import com.babzip.backend.global.exception.ExceptionType;

public class JwtNotExistException extends JwtAuthenticationException {
    public JwtNotExistException() {
        super(ExceptionType.JWT_NOT_EXIST);
    }
}
