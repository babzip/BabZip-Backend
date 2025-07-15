package com.babzip.backend.guestbook.repository;

import com.babzip.backend.guestbook.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
    List<Guestbook> findByKakaoPlaceId(String kakaoPlaceId);
    List<Guestbook> findByUserId(Long userId);
    Optional<Guestbook> findByIdAndUserId(Long guestbookId, Long userId);
}
