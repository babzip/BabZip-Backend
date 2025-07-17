package com.babzip.backend.top10.domain;

import com.babzip.backend.global.base.BaseEntity;
import com.babzip.backend.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Top10 extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long userId;

    private String restaurantName;
    private String address;
    private Long rank;

    @Builder
    public Top10(Long userId,String restaurantName, String address, Long rank) {
        this.userId = userId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.rank = rank;
    }
}
