package com.babzip.backend.user.repository;

import com.babzip.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
