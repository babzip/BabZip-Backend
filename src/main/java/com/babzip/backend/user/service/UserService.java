package com.babzip.backend.user.service;

import com.babzip.backend.global.exception.BusinessException;
import com.babzip.backend.global.exception.ExceptionType;
import com.babzip.backend.token.repository.RefreshTokenRepository;
import com.babzip.backend.user.domain.User;
import com.babzip.backend.user.domain.UserRole;
import com.babzip.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public boolean isAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BusinessException(ExceptionType.USER_NOT_FOUND));

        System.out.println("유저의 권한: " + user.getRole());
        System.out.println("어드민 권한이 일치한지 확인한다 : " + UserRole.ADMIN);
        return user.getRole().equals(UserRole.ADMIN);
    }

    @Transactional
    public void logout(Long userId){
        refreshTokenRepository.deleteByUserId(userId);
    }
}
