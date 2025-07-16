package com.babzip.backend.guestbook.controller;

import com.babzip.backend.global.aop.AssignUserId;
import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.global.response.ResponseUtil;
import com.babzip.backend.guestbook.dto.request.GuestbookRequestDto;
import com.babzip.backend.guestbook.dto.response.GuestbookResponseDto;
import com.babzip.backend.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/guestbook")
public class GuestbookController {

    private final GuestbookService guestbookService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> create(@AssignUserId Long userId,
                                                     @RequestBody GuestbookRequestDto requestDto) {
        guestbookService.create(userId, requestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Page<GuestbookResponseDto>>> getMyGuestbooks(
            @AssignUserId Long userId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<GuestbookResponseDto> response = guestbookService.getByUserId(userId, pageable);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(response));
    }

    @PutMapping("/{guestbookId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> updateAll(@AssignUserId Long userId,
                                                        @PathVariable Long guestbookId,
                                                        @RequestBody GuestbookRequestDto requestDto) {
        guestbookService.updateAll(userId, guestbookId, requestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @PatchMapping("/{guestbookId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> updatePartial(@AssignUserId Long userId,
                                                            @PathVariable Long guestbookId,
                                                            @RequestBody GuestbookRequestDto requestDto) {
        guestbookService.updatePartial(userId, guestbookId, requestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @DeleteMapping("/{guestbookId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> delete(@AssignUserId Long userId,
                                                     @PathVariable Long guestbookId) {
        guestbookService.delete(userId, guestbookId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}