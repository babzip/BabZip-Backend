package com.babzip.backend.guestbook.controller;

import com.babzip.backend.guestbook.dto.request.GuestbookRequestDto;
import com.babzip.backend.guestbook.dto.response.GuestbookResponseDto;
import com.babzip.backend.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guestbooks")
@RequiredArgsConstructor
public class GuestbookController {
    private final GuestbookService guestbookService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestParam Long userId,
                                       @RequestBody GuestbookRequestDto dto) {
        guestbookService.create(userId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/restaurant/{kakaoPlaceId}")
    public ResponseEntity<List<GuestbookResponseDto>> getByKakaoPlaceId(@PathVariable String kakaoPlaceId) {
        return ResponseEntity.ok(guestbookService.getByKakaoPlaceId(kakaoPlaceId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<GuestbookResponseDto>> getMyGuestbooks(@RequestParam Long userId) {
        return ResponseEntity.ok(guestbookService.getByUserId(userId));
    }

    @PutMapping("/{guestbookId}")
    public ResponseEntity<Void> update(@PathVariable Long guestbookId,
                                       @RequestParam Long userId,
                                       @RequestBody GuestbookRequestDto dto) {
        guestbookService.update(guestbookId, userId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{guestbookId}")
    public ResponseEntity<Void> delete(@PathVariable Long guestbookId,
                                       @RequestParam Long userId) {
        guestbookService.delete(guestbookId, userId);
        return ResponseEntity.ok().build();
    }
}
