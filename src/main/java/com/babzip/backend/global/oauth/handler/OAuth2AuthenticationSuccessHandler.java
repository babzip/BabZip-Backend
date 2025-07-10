package com.babzip.backend.global.oauth.handler;

import com.babzip.backend.global.jwt.JwtHandler;
import com.babzip.backend.global.jwt.JwtUserClaim;
import com.babzip.backend.global.jwt.TokenProvider;
import com.babzip.backend.global.oauth.service.OAuth2UserPrincipal;
import com.babzip.backend.global.oauth.user.OAuth2Provider;
import com.babzip.backend.global.token.entity.Token;
import com.babzip.backend.user.domain.User;
import com.babzip.backend.user.domain.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtHandler jwtHandler;
    private static final String URI = "/auth/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2UserPrincipal principal = (OAuth2UserPrincipal) authentication.getPrincipal();
        Long userId = principal.getUser().getId();
        UserRole role = principal.getUser().getRole();

        JwtUserClaim jwtUserClaim = new JwtUserClaim(userId,role);
        Token token = jwtHandler.createTokens(jwtUserClaim);

        // 토큰 전달을 위한 redirect
        String redirectUrl = UriComponentsBuilder.fromUriString(URI)
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("refreshToken", token.getRefreshToken())
                .build().toUriString();
        System.out.println(redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
