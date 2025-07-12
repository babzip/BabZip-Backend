package com.babzip.backend.search.service;

import com.babzip.backend.global.oauth.user.KakaoProperties;
import com.babzip.backend.search.dto.request.SearchRequest;
import com.babzip.backend.search.dto.response.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final KakaoProperties kakaoProperties;
    private final WebClient webClient;

    public SearchResponse search(SearchRequest request){

        String query = request.query();
        String x = request.x();
        String y = request.y();
        Long page = request.page();
        String groupCode = "FD6";
        Long radius = 10000L;

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/keyword.json")
                        .queryParam("query", query)
                        .queryParam("category_group_code", groupCode)
                        .queryParam("x",x)
                        .queryParam("y",y)
                        .queryParam("radius",radius)
                        .queryParam("page",page)
                        .build())
                .retrieve()
                .bodyToMono(SearchResponse.class)
                .block(Duration.ofSeconds(3));
    }

}
