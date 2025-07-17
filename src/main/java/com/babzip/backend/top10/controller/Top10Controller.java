package com.babzip.backend.top10.controller;

import com.babzip.backend.global.aop.AssignUserId;
import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.top10.api.Top10Api;
import com.babzip.backend.top10.dto.request.Top10Request;
import com.babzip.backend.top10.dto.response.Top10Response;
import com.babzip.backend.top10.service.Top10Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.babzip.backend.global.response.ResponseUtil.createSuccessResponse;

@RestController
@RequestMapping("/top10")
@RequiredArgsConstructor
public class Top10Controller implements Top10Api {

    private final Top10Service top10Service;

    @AssignUserId
    @PostMapping
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<ResponseBody<Void>> createTop10(
            Long userId,
            @RequestBody List<Top10Request> request
    ){
        top10Service.createTop10(userId, request);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @AssignUserId
    @GetMapping
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<ResponseBody<Page<Top10Response>>> getTop10(
            Long userId,
            @PageableDefault(sort = "rank", direction = Sort.Direction.ASC) Pageable pageable){
        Page<Top10Response> response = top10Service.getTop10(userId, pageable);
        return ResponseEntity.ok(createSuccessResponse(response));
    }
}
