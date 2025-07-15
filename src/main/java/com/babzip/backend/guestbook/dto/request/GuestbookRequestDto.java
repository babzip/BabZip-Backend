package com.babzip.backend.guestbook.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GuestbookRequestDto {
    private String kakaoPlaceId; // 카카오에서 받은 음식점 ID
    private String content;
    private Long rating;
}

