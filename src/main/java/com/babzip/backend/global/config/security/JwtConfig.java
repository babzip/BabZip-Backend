package com.babzip.backend.global.config.security;

import com.babzip.backend.global.jwt.JwtHandler;
import com.babzip.backend.global.jwt.JwtProperties;
import com.babzip.backend.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public JwtHandler jwtHandler(JwtProperties jwtProperties) {
        return new JwtHandler(jwtProperties, refreshTokenRepository);
    }
}
