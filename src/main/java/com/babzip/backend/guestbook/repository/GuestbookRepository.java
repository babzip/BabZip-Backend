package com.babzip.backend.guestbook.repository;

import com.babzip.backend.guestbook.entity.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
    Page<Guestbook> findByUserId(Long userId, Pageable pageable);
    Optional<Guestbook> findByIdAndUserId(Long id, Long userId);
    Optional<Guestbook> findByKakaoPlaceIdAndUserId(String kakaoPlaceId, Long userId);
}
