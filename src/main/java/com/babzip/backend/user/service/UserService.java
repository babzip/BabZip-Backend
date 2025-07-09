package com.babzip.backend.user.service;

import com.babzip.backend.global.exception.BusinessException;
import com.babzip.backend.global.exception.ExceptionType;
import com.babzip.backend.user.domain.User;
import com.babzip.backend.user.domain.UserRole;
import com.babzip.backend.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;

    public boolean isAdmin(Long userId) {
        User user = memberRepository.findById(userId)
                .orElseThrow(()->new BusinessException(ExceptionType.USER_NOT_FOUND));

        System.out.println("유저의 권한: " + user.getRole());
        System.out.println("어드민 권한이 일치한지 확인한다 : " + UserRole.ADMIN);
        return user.getRole().equals(UserRole.ADMIN);
    }
}
