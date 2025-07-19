package com.babzip.backend.user.dto.response;

import com.babzip.backend.user.domain.User;

public record UserProfileResponse (
    String name,
    String picture,
    Long restaurantCount,
    Double averageRating,
    String provider
){
    public static UserProfileResponse toDto(User user, Long restaurantCount, Double averageRating){
        return new UserProfileResponse(
                user.getName(),
                user.getPicture(),
                restaurantCount,
                averageRating,
                user.getProvider().toString()
        );
    }
}
