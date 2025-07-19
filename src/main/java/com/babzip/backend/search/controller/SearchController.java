package com.babzip.backend.search.controller;

import com.babzip.backend.global.aop.AssignUserId;
import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.search.api.SearchApi;
import com.babzip.backend.search.dto.request.SearchRequest;
import com.babzip.backend.search.dto.response.KakaoSearchResponse;
import com.babzip.backend.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.babzip.backend.global.response.ResponseUtil.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchController implements SearchApi {

    private final SearchService searchService;

    @AssignUserId
    @PostMapping("/search")
    @PreAuthorize(" isAuthenticated()")
    public ResponseEntity<ResponseBody<KakaoSearchResponse>> search(@RequestBody SearchRequest request, Long userId){
        KakaoSearchResponse response = searchService.search(request, userId);
        return ResponseEntity.ok(createSuccessResponse(response));
    }
}
