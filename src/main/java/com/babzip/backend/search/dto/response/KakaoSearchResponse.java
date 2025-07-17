package com.babzip.backend.search.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // 역직렬화 시 JSON에 DTO에 없는 필드가 들어 있어도 무시하고 오류를 내지 않음
public record KakaoSearchResponse(
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
            String y,
            boolean isExist
    ) {}


    public KakaoSearchResponse toResponse(KakaoSearchResponse response, List<String> registeredPlaceIds) {
        List<Document> updatedDocuments = response.documents().stream()
                .map(doc -> new Document(
                        doc.placeName(),
                        doc.distance(),
                        doc.placeUrl(),
                        doc.categoryName(),
                        doc.addressName(),
                        doc.roadAddressName(),
                        doc.id(),
                        doc.phone(),
                        doc.x(),
                        doc.y(),
                        registeredPlaceIds.contains(doc.id())
                ))
                .toList();

        return new KakaoSearchResponse(response.meta(), updatedDocuments);
    }
}
