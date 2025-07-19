package com.babzip.backend.guestbook.controller;

import com.babzip.backend.global.aop.AssignUserId;
import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.global.response.ResponseUtil;
import com.babzip.backend.guestbook.api.GuestBookApi;
import com.babzip.backend.guestbook.dto.request.GuestbookRequestDto;
import com.babzip.backend.guestbook.dto.response.GuestbookResponseDto;
import com.babzip.backend.guestbook.dto.response.GuestbookSearchResponse;
import com.babzip.backend.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/guestbook")
public class GuestbookController implements GuestBookApi {

    private final GuestbookService guestbookService;

    @AssignUserId
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> create(
            Long userId,
            @RequestBody GuestbookRequestDto requestDto
    ) {
        guestbookService.create(userId, requestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @AssignUserId
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Page<GuestbookResponseDto>>> getMyGuestbooks(
            Long userId,
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<GuestbookResponseDto> response = guestbookService.getByUserId(userId, pageable);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(response));
    }

    @AssignUserId
    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> updatePartial(
            Long userId,
            @RequestBody GuestbookRequestDto requestDto
    ) {
        guestbookService.updatePartial(userId, requestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @AssignUserId
    @DeleteMapping("/{kakaoPlaceId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> delete(
            Long userId,
            @PathVariable String kakaoPlaceId) {
        guestbookService.delete(userId, kakaoPlaceId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @AssignUserId
    @GetMapping("/search/{query}")
    public ResponseEntity<ResponseBody<Page<GuestbookSearchResponse>>> search(
            Long userId,
            @PathVariable String query,
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(guestbookService.search(query, pageable, userId)));
    }
}