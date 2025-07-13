package com.babzip.backend.search.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // 역직렬화 시 JSON에 DTO에 없는 필드가 들어 있어도 무시하고 오류를 내지 않음
public record SearchResponse(
        Meta meta,
        List<Document> documents
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Meta(
            @JsonProperty("is_end") boolean isEnd
    ) {}

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Document(
            String placeName,
            String distance,
            String placeUrl,
            String categoryName,
            String addressName,
            String roadAddressName,
            String id,
            String phone,
            String x,
            String y
    ) {}
}
