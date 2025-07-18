package com.babzip.backend.guestbook.service;

import com.babzip.backend.global.exception.BusinessException;
import com.babzip.backend.global.exception.ExceptionType;
import com.babzip.backend.guestbook.dto.request.GuestbookRequestDto;
import com.babzip.backend.guestbook.dto.response.GuestbookResponseDto;
import com.babzip.backend.guestbook.dto.response.GuestbookSearchResponse;
import com.babzip.backend.guestbook.entity.Guestbook;
import com.babzip.backend.guestbook.repository.GuestbookRepository;
import com.babzip.backend.user.domain.User;
import com.babzip.backend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(Long userId, GuestbookRequestDto dto) {
        User user = getUser(userId);
        Guestbook guestbook = Guestbook.builder()
                .restaurantName(dto.restaurantName())
                .address(dto.address())
                .user(user)
                .kakaoPlaceId(dto.kakaoPlaceId())
                .content(dto.content())
                .rating(dto.rating())
                .build();
        guestbookRepository.save(guestbook);
    }

    @Transactional(readOnly = true)
    public Page<GuestbookResponseDto> getByUserId(Long userId, Pageable pageable) {
        return guestbookRepository.findByUserId(userId, pageable)
                .map(GuestbookResponseDto::toDto);
    }

    @Transactional
    public void updatePartial(Long userId, Long guestbookId, GuestbookRequestDto dto) {
        Guestbook guestbook = getOwnedGuestbook(userId, guestbookId);
        guestbook.updatePartial(dto.restaurantName(), dto.kakaoPlaceId(), dto.content(), dto.rating());
    }

    @Transactional
    public void delete(Long userId, Long guestbookId) {
        Guestbook guestbook = getOwnedGuestbook(userId, guestbookId);
        guestbookRepository.delete(guestbook);
    }

    private Guestbook getOwnedGuestbook(Long userId, Long guestbookId) {
        return guestbookRepository.findByIdAndUserId(guestbookId, userId)
                .orElseThrow(() -> new BusinessException(ExceptionType.GUEST_BOOK_NOT_FOUND));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ExceptionType.USER_NOT_FOUND));
    }

    public Page<GuestbookSearchResponse> search(String query, Pageable pageable, Long userId){
        return guestbookRepository.searchByRestaurantName(query, pageable, userId);
    }
}
