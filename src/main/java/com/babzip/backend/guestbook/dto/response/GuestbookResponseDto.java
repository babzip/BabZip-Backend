package com.babzip.backend.guestbook.dto.response;

import com.babzip.backend.guestbook.entity.Guestbook;

public record GuestbookResponseDto(
        Long id,
        String kakaoPlaceId,
        String content,
        Integer rating
) {
    public static GuestbookResponseDto toDto(Guestbook guestbook) {
        return new GuestbookResponseDto(
                guestbook.getId(),
                guestbook.getKakaoPlaceId(),
                guestbook.getContent(),
                guestbook.getRating()
        );
    }
}