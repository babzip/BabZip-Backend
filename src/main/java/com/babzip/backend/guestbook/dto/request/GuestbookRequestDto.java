package com.babzip.backend.guestbook.dto.request;


import lombok.Builder;

public record GuestbookRequestDto (
        String restaurantName,
        String address,
        String kakaoPlaceId, // 카카오에서 받은 음식점 ID
        String content,
        Double rating
){
}

