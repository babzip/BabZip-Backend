package com.babzip.backend.global.token.service;

import com.babzip.backend.global.exception.BusinessException;
import com.babzip.backend.global.exception.ExceptionType;
import com.babzip.backend.global.jwt.JwtHandler;
import com.babzip.backend.global.jwt.JwtUserClaim;
import com.babzip.backend.global.token.dto.response.TokenResponse;
import com.babzip.backend.global.token.entity.RefreshToken;
import com.babzip.backend.global.token.entity.Token;
import com.babzip.backend.global.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtHandler jwtHandler;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenResponse refresh(Token token) {
        JwtUserClaim jwtUserClaim = jwtHandler.getClaims(token.getAccessToken())
                .orElseThrow(() -> new BusinessException(ExceptionType.JWT_INVALID)); // invalid token

        RefreshToken savedRefreshToken = refreshTokenRepository.findByUserId(jwtUserClaim.userId())
                .orElseThrow(() -> new BusinessException(ExceptionType.REFRESH_TOKEN_NOT_EXIST)); // not exist token

        if(!token.getRefreshToken().equals(savedRefreshToken.getRefreshToken())) // userId 비교
            throw new BusinessException(ExceptionType.TOKEN_NOT_MATCHED);

        refreshTokenRepository.deleteByuserId(savedRefreshToken.getUserId());

        Token tokenResponse = jwtHandler.createTokens(jwtUserClaim);
        return new TokenResponse(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());
    }


}
