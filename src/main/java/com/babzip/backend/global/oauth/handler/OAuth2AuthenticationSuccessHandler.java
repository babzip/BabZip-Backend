package com.babzip.backend.global.oauth.handler;

import com.babzip.backend.global.jwt.JwtHandler;
import com.babzip.backend.global.jwt.JwtUserClaim;
import com.babzip.backend.global.oauth.service.OAuth2UserPrincipal;
import com.babzip.backend.token.entity.Token;
import com.babzip.backend.user.domain.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtHandler jwtHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2UserPrincipal principal = (OAuth2UserPrincipal) authentication.getPrincipal();
        Long userId = principal.getUser().getId();
        UserRole role = principal.getUser().getRole();

        JwtUserClaim jwtUserClaim = new JwtUserClaim(userId,role);
        Token token = jwtHandler.createTokens(jwtUserClaim);

        String targetUrl = "http://localhost:5173";

        // 토큰 붙여서 리다이렉트
        String redirectUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("refreshToken", token.getRefreshToken())
                .build().toUriString();

        System.out.println(redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
