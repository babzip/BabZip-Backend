package com.babzip.backend.global.jwt.exception;

import com.babzip.backend.global.exception.ExceptionType;

public class JwtAccessDeniedException extends JwtAuthenticationException {
    public JwtAccessDeniedException() {
        super(ExceptionType.ACCESS_DENIED);
    }
}
