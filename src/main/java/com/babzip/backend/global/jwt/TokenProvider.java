package com.babzip.backend.global.jwt;

import com.babzip.backend.global.jwt.exception.JwtAccessDeniedException;
import com.babzip.backend.global.jwt.exception.JwtAuthentication;
import com.babzip.backend.global.jwt.exception.JwtTokenExpiredException;
import com.babzip.backend.global.jwt.exception.JwtTokenInvalidException;
import com.babzip.backend.user.domain.UserRole;
import com.babzip.backend.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class TokenProvider implements AuthenticationProvider {

    private final JwtHandler jwtHandler;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String tokenValue = jwtAuthenticationToken.token();
        if (tokenValue == null) {
            System.out.println("null이에요");
            return null;
        }
        System.out.println("널이 아니에여ㅛ");
        try {
            JwtUserClaim claims = jwtHandler.parseToken(tokenValue);
            this.validateAdminRole(claims);
            return new JwtAuthentication(claims);
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException(e);
        } catch (JwtAccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            throw new JwtTokenInvalidException(e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private void validateAdminRole(JwtUserClaim claims) {
        Long userId = claims.userId();
        if (UserRole.ADMIN.equals(claims.role()) && !userService.isAdmin(userId)) {
            throw new JwtAccessDeniedException();
        }
    }
}