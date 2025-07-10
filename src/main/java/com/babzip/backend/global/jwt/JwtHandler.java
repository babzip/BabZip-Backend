package com.babzip.backend.global.jwt;

import com.babzip.backend.global.token.entity.RefreshToken;
import com.babzip.backend.global.token.entity.Token;
import com.babzip.backend.global.token.repository.RefreshTokenRepository;
import com.babzip.backend.user.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class JwtHandler {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;
    private final RefreshTokenRepository refreshTokenRepository;
    public static final String USER_ID = "USER_ID";
    public static final String USER_ROLE = "USER_ROLE";
    private static final String KEY_ROLE = "role";
    private static final long MILLI_SECOND = 1000L;

    public JwtHandler(JwtProperties jwtProperties, RefreshTokenRepository refreshTokenRepository) {
        this.jwtProperties = jwtProperties;
        this.refreshTokenRepository = refreshTokenRepository;
        secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // TODO : 추후에 제거
    // accessToken 발급
    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, jwtProperties.getAccessTokenExpireIn() * MILLI_SECOND);
    }

    // TODO : 추후에 제거
    // refreshToken 발급
    public void generateRefreshToken(Authentication authentication, String accessToken) {
        // 랜덤 UUID로 refreshToken 생성
        String refreshToken = UUID.randomUUID().toString();

        // Jwt에서 클레임을 추출
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();

        // 클레임의 userId 추출
        Long userId = claims.get("USER_ID", Long.class);
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .build();
        refreshTokenRepository.save(refreshTokenEntity); // refreshToken 저장
    }

    public Token createTokens(JwtUserClaim jwtUserClaim) {
        Map<String, Object> tokenClaims = this.createClaims(jwtUserClaim);
        Date now = new Date(System.currentTimeMillis());
        long accessTokenExpireIn = jwtProperties.getAccessTokenExpireIn();

        String accessToken = Jwts.builder()
                .claims(tokenClaims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessTokenExpireIn * MILLI_SECOND))
                .signWith(secretKey)
                .compact();

        String refreshToken = UUID.randomUUID().toString();

        RefreshToken refreshTokenEntity = new RefreshToken(jwtUserClaim.userId(), refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Map<String, Object> createClaims(JwtUserClaim jwtUserClaim) {
        return Map.of(
                USER_ID, jwtUserClaim.userId(),
                USER_ROLE, jwtUserClaim.role()
        );
    }

    // TODO 추후에 제거
    private String generateToken(Authentication authentication, long expireTime) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return Jwts.builder()
                .claim(KEY_ROLE, authorities)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    // TODO 이 코드들을 JwtAuthenticationFilter 로 옮겨야됨
//    public Authentication getAuthentication(String token) {
//        Claims claims = parseClaims(token);
//        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
//
//        // 2. security의 User 객체 생성
//        User principal = new User(claims.getSubject(), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }
//
//    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
//        return Collections.singletonList(new SimpleGrantedAuthority(
//                claims.get(KEY_ROLE).toString()));
//    }


    // TODO : 중복되는 로직. 액세스 토큰 재발급 api 개발 후 제거하기
    // accessToken 재발급
    // 클레임을 받아와서 클레임의 userId로 refreshToken을 찾음
//    @Deprecated
//    public String reissueAccessToken(String accessToken) {
//        if (StringUtils.hasText(accessToken)) {
//
//            // Jwt에서 클레임을 추출
//            Claims claims = Jwts.parser()
//                    .verifyWith(secretKey)
//                    .build()
//                    .parseSignedClaims(accessToken)
//                    .getPayload();
//
//            // 클레임의 userId 추출
//            Long userId = claims.get(USER_ID, Long.class);
//            RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(userId)
//                    .orElseThrow(() -> new RuntimeException("refreshToken not found"));
//            String refreshToken = refreshTokenEntity.getRefreshToken();
//
//            // 리프래시 토큰이 유효하면 액세스 토큰 재발급
//            if (validateToken(refreshToken)) {
//                String reissueAccessToken = generateAccessToken(getAuthentication(refreshToken));
//                return reissueAccessToken;
//            }
//        }
//        return null;
//    }


    // 재발급을 위해 token이 만료되었더라도 claim을 반환하는 메서드
    public Optional<JwtUserClaim> getClaims(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Optional.of(this.convert(claims));
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            return Optional.of(this.convert(claims));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public JwtUserClaim convert(Claims claims) {
        return new JwtUserClaim(
                claims.get(USER_ID, Long.class),
                UserRole.valueOf(claims.get(USER_ROLE, String.class))
        );
    }

    // TODO : JwtAuthenticationFilter 개발 후 제거
//    public boolean validateToken(String token) {
//        if (!StringUtils.hasText(token)) {
//            return false;
//        }
//
//        Claims claims = parseClaims(token);
//        return claims.getExpiration().after(new Date());
//    }

    // TODO : JwtAuthenticationFilter 개발 후 제거
//    private Claims parseClaims(String token) {
//        try {
//            return Jwts.parser().verifyWith(secretKey).build()
//                    .parseSignedClaims(token).getPayload();
//        } catch (ExpiredJwtException e) {
//            return e.getClaims();
//        } catch (MalformedJwtException e) {
//            throw e;
////            throw new TokenException(INVALID_TOKEN);
//        } catch (SecurityException e) {
////            throw new TokenException(INVALID_JWT_SIGNATURE);
//            throw e;
//        }
//    }

    // 필터에서 토큰의 상태를 검증하기 위한 메서드 exception은 사용하는 곳에서 처리
    public JwtUserClaim parseToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return this.convert(claims);
    }
}
