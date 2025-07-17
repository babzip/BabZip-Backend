package com.babzip.backend.guestbook.entity;

import com.babzip.backend.global.base.BaseEntity;
import com.babzip.backend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guestbook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantName;
    private String kakaoPlaceId;
    private String content;
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Guestbook(String restaurantName, User user, String kakaoPlaceId, String content, Integer rating) {
        this.restaurantName = restaurantName;
        this.user = user;
        this.kakaoPlaceId = kakaoPlaceId;
        this.content = content;
        this.rating = rating;
    }

    public void updatePartial(String restaurantName,String kakaoPlaceId, String content, Integer rating) {
        if (restaurantName != null) this.restaurantName = restaurantName;
        if (kakaoPlaceId != null) this.kakaoPlaceId = kakaoPlaceId;
        if (content != null) this.content = content;
        if (rating != null) this.rating = rating;
    }
}