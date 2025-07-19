package com.babzip.backend.guestbook.repository;

import com.babzip.backend.guestbook.entity.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, GuestbookQueryDslRepository {
    Page<Guestbook> findByUserId(Long userId, Pageable pageable);
    Optional<Guestbook> findByIdAndUserId(Long id, Long userId);
    Optional<Guestbook> findByKakaoPlaceIdAndUserId(String kakaoPlaceId, Long userId);

    @Query("SELECT g.kakaoPlaceId FROM Guestbook g WHERE g.user.id = :userId")
    List<String> findAllKakaoPlaceIdByUserId(Long userId);

    Long countByUserId(Long userId);

    @Query("SELECT AVG(g.rating) FROM Guestbook g WHERE g.user.id = :userId")
    Double findAverageRatingByUserId(Long userId);
}
