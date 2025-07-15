package com.babzip.backend.guestbook.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GuestbookResponseDto {
    private Long guestbookId;
    private String kakaoPlaceId;
    private String content;
    private Long rating;
}
