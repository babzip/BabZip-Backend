package com.babzip.backend.top10.dto.request;

public record Top10Request (
        String restaurantName,
        String address,
        Long rankValue
){
}
