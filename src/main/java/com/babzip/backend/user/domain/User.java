package com.babzip.backend.user.domain;

import com.babzip.backend.global.base.BaseEntity;
import com.babzip.backend.global.oauth.user.OAuth2Provider;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(unique = true)
    private String email;

//    @Column(unique = true)
//    private String nickname;

    @Enumerated(value = EnumType.STRING)
    @Getter
    private UserRole role;

    @Enumerated(value = EnumType.STRING)
    private OAuth2Provider provider;

    private String providerId;

    @Builder
    public User(String email, UserRole role, OAuth2Provider provider, String providerId) {
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
