package com.babzip.backend.search.controller;

import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.search.dto.request.SearchRequest;
import com.babzip.backend.search.dto.response.SearchResponse;
import com.babzip.backend.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.babzip.backend.global.response.ResponseUtil.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    @PreAuthorize(" isAuthenticated()")
    public ResponseEntity<ResponseBody<SearchResponse>> search(@RequestBody SearchRequest request){
        SearchResponse response = searchService.search(request);
        return ResponseEntity.ok(createSuccessResponse(response));
    }
}
