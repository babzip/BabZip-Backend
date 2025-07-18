package com.babzip.backend.top10.dto.response;

import com.babzip.backend.top10.domain.Top10;

public record Top10Response (
        String restaurantName,
        String address,
        Long rankValue
){
    public static Top10Response toDto(Top10 top10) {
        return new Top10Response(
                top10.getRestaurantName(),
                top10.getAddress(),
                top10.getRankValue()
        );
    }

    public static Top10Response empty() {
        return new Top10Response(null, null, null);
    }
}
