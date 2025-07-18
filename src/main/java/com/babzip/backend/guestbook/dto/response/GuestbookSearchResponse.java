package com.babzip.backend.guestbook.dto.response;

import com.babzip.backend.guestbook.entity.Guestbook;

import java.time.LocalDateTime;

public record GuestbookSearchResponse (
        String restaurantName,
        String address,
        Long rating,
        LocalDateTime createdAt
){
    public static GuestbookSearchResponse toDto(Guestbook guestbook) {
        return new GuestbookSearchResponse(
                guestbook.getRestaurantName(),
                guestbook.getAddress(),
                guestbook.getRating(),
                guestbook.getCreatedAt()
        );
    }
}
