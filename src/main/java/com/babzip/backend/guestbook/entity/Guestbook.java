package com.babzip.backend.guestbook.entity;

import com.babzip.backend.global.base.BaseEntity;
import com.babzip.backend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Guestbook extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestbookId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String kakaoPlaceId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private Long rating;
}