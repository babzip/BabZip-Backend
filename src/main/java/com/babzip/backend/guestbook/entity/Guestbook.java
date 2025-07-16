package com.babzip.backend.guestbook.entity;

import com.babzip.backend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor


public class Guestbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kakaoPlaceId;
    private String content;
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Guestbook(User user, String kakaoPlaceId, String content, Integer rating) {
        this.user = user;
        this.kakaoPlaceId = kakaoPlaceId;
        this.content = content;
        this.rating = rating;
    }

    public void updateAll(String kakaoPlaceId, String content, Integer rating) {
        this.kakaoPlaceId = kakaoPlaceId;
        this.content = content;
        this.rating = rating;
    }

    public void updatePartial(String content, Integer rating) {
        if (content != null) this.content = content;
        if (rating != null) this.rating = rating;
    }
}