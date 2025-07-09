package com.babzip.backend.global.oauth.user;

import com.babzip.backend.global.exception.BusinessException;
import com.babzip.backend.user.domain.User;
import jakarta.security.auth.message.AuthException;
import lombok.Builder;
import java.util.Map;
import com.babzip.backend.global.exception.ExceptionType;
import lombok.Getter;

import static com.babzip.backend.user.domain.UserRole.USER;

@Builder
public record OAuth2UserInfo(
        String name,
        String email,
        String profile
) {

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) { // registration id별로 userInfo 생성
            case "google" -> ofGoogle(attributes);
            case "kakao" -> ofKakao(attributes);
            default -> throw new BusinessException(ExceptionType.ILLEGAL_REGISTRATION_ID);
        };
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .build();
    }

    // TODO : 구글 도입 후에 카카오 도입
    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .profile((String) profile.get("profile_image_url"))
                .build();
    }


//    public User toEntity() {
//        return User.builder()
//                .email(email)
//                .role(USER)
//                .provider(provider)
//                .build()
//    }
}