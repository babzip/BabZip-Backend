package com.babzip.backend.guestbook.service;

import com.babzip.backend.guestbook.dto.request.GuestbookRequestDto;
import com.babzip.backend.guestbook.dto.response.GuestbookResponseDto;
import com.babzip.backend.guestbook.entity.Guestbook;
import com.babzip.backend.guestbook.repository.GuestbookRepository;
import com.babzip.backend.user.domain.User;
import com.babzip.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestbookService {
    private final GuestbookRepository guestbookRepository;
    private final UserRepository userRepository;

    public void create(Long userId, GuestbookRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        Guestbook guestbook = new Guestbook();
        guestbook.setUser(user);
        guestbook.setKakaoPlaceId(dto.getKakaoPlaceId());
        guestbook.setContent(dto.getContent());
        guestbook.setRating(dto.getRating());

        guestbookRepository.save(guestbook);
    }

    public List<GuestbookResponseDto> getByKakaoPlaceId(String kakaoPlaceId) {
        return guestbookRepository.findByKakaoPlaceId(kakaoPlaceId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<GuestbookResponseDto> getByUserId(Long userId) {
        return guestbookRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void update(Long guestbookId, Long userId, GuestbookRequestDto dto) {
        Guestbook guestbook = guestbookRepository.findByIdAndUserId(guestbookId, userId)
                .orElseThrow(() -> new IllegalArgumentException("수정 권한이 없습니다."));

        guestbook.setContent(dto.getContent());
        guestbook.setRating(dto.getRating());
    }

    public void delete(Long guestbookId, Long userId) {
        Guestbook guestbook = guestbookRepository.findByIdAndUserId(guestbookId, userId)
                .orElseThrow(() -> new IllegalArgumentException("삭제 권한이 없습니다."));

        guestbookRepository.delete(guestbook);
    }

    private GuestbookResponseDto toDto(Guestbook g) {
        return new GuestbookResponseDto(
                g.getGuestbookId(),
                g.getKakaoPlaceId(),
                g.getContent(),
                g.getRating()
        );
    }
}
