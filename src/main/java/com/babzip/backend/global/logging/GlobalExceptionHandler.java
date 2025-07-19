package com.babzip.backend.global.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 모든 예외 처리 (500 에러 포함)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        log.error("🔥 500 에러 발생! 예외 메시지: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다.");
    }
}