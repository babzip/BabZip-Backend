package com.babzip.backend.top10.repository;

import com.babzip.backend.top10.domain.Top10;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Top10Repository extends JpaRepository<Top10, Long> {
    void deleteByUserId(Long userId);
    Page<Top10> findByUserId(Long userId, Pageable pageable);
}
