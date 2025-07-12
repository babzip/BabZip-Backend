package com.babzip.backend.search.dto.request;

public record SearchRequest (
    String query,
    String x, // 경도
    String y // 위도
){

}
