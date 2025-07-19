package com.babzip.backend.guestbook.dto.response;

import com.babzip.backend.guestbook.entity.Guestbook;
import java.time.LocalDateTime;

public record GuestbookResponseDto(
        String restaurantName,
        String kakaoPlaceId,
        String content,
        Long rating,
        String address,
        LocalDateTime createdAt
) {
    public static GuestbookResponseDto toDto(Guestbook guestbook) {
        return new GuestbookResponseDto(
                guestbook.getRestaurantName(),
                guestbook.getKakaoPlaceId(),
                guestbook.getContent(),
                guestbook.getRating(),
                guestbook.getAddress(),
                guestbook.getCreatedAt()

        );
    }
}