package com.babzip.backend.global.token.controller;

import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.global.token.dto.request.TokenRequest;
import com.babzip.backend.global.token.dto.response.TokenResponse;
import com.babzip.backend.global.token.entity.Token;
import com.babzip.backend.global.token.service.TokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.babzip.backend.global.response.ResponseUtil.createSuccessResponse;

@RestController
@RequestMapping("/auth/token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/refresh")
    public ResponseEntity<ResponseBody<TokenResponse>> refresh(@RequestBody  TokenRequest tokenRequest) {
        Token token = new Token(tokenRequest.accessToken(), tokenRequest.refreshToken());
        TokenResponse response = tokenService.refresh(token);
        return ResponseEntity.ok(createSuccessResponse(response));
    }
}
