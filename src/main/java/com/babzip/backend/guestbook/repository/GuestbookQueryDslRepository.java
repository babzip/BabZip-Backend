package com.babzip.backend.guestbook.repository;

import com.babzip.backend.guestbook.dto.response.GuestbookSearchResponse;
import com.babzip.backend.guestbook.entity.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GuestbookQueryDslRepository {
    Page<GuestbookSearchResponse> searchByRestaurantName(String restaurantName, Pageable pageable, Long userId);
}
