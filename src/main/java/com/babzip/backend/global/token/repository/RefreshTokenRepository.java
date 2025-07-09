package com.babzip.backend.global.token.repository;

import com.babzip.backend.global.token.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(Long userId);
    void deleteByuserId(Long userId);
}
