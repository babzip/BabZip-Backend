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
    private String address;
    private String kakaoPlaceId;
    private String content;
    private Long rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Guestbook(String restaurantName, String address,User user, String kakaoPlaceId, String content, Long rating) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.user = user;
        this.kakaoPlaceId = kakaoPlaceId;
        this.content = content;
        this.rating = rating;
    }

    public void updatePartial(String restaurantName,String kakaoPlaceId, String content, Long rating) {
        if (restaurantName != null) this.restaurantName = restaurantName;
        if (kakaoPlaceId != null) this.kakaoPlaceId = kakaoPlaceId;
        if (content != null) this.content = content;
        if (rating != null) this.rating = rating;
    }
}