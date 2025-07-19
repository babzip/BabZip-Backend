package com.babzip.backend.guestbook.service;

import com.babzip.backend.guestbook.dto.request.GuestbookRequestDto;
import com.babzip.backend.guestbook.dto.response.GuestbookResponseDto;
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
        Guestbook guestbook = new Guestbook(user, dto.getKakaoPlaceId(), dto.getContent(), dto.getRating());
        guestbookRepository.save(guestbook);
    }

    @Transactional(readOnly = true)
    public Page<GuestbookResponseDto> getByUserId(Long userId, Pageable pageable) {
        return guestbookRepository.findByUserId(userId, pageable)
                .map(GuestbookResponseDto::toDto);
    }

    @Transactional
    public void updatePartial(Long userId, String kakaoPlaceId, GuestbookRequestDto dto) {
        Guestbook guestbook = getGuestbookByPlaceAndUser(userId, kakaoPlaceId);
        guestbook.updatePartial(dto.getKakaoPlaceId(), dto.getContent(), dto.getRating());
    }

    @Transactional
    public void delete(Long userId, String kakaoPlaceId) {
        Guestbook guestbook = getGuestbookByPlaceAndUser(userId, kakaoPlaceId);
        guestbookRepository.delete(guestbook);
    }

    private Guestbook getGuestbookByPlaceAndUser(Long userId, String kakaoPlaceId) {
        return guestbookRepository.findByKakaoPlaceIdAndUserId(kakaoPlaceId, userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 방명록을 찾을 수 없습니다."));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
