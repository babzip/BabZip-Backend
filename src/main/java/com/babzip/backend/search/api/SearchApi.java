package com.babzip.backend.search.api;

import com.babzip.backend.global.aop.AssignUserId;
import com.babzip.backend.global.config.swagger.SwaggerApiFailedResponse;
import com.babzip.backend.global.config.swagger.SwaggerApiResponses;
import com.babzip.backend.global.config.swagger.SwaggerApiSuccessResponse;
import com.babzip.backend.global.exception.ExceptionType;
import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.search.dto.request.SearchRequest;
import com.babzip.backend.search.dto.response.KakaoSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "검색 API", description = "검색 관련 API")
public interface SearchApi {
    @Operation(
            summary = "음식점 검색",
            description = "사용자는 반경 10km 내의 음식점을 검색합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = KakaoSearchResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    responsePage = KakaoSearchResponse.class,
                    description = "음식점 검색 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
            }
    )
    @AssignUserId
    @GetMapping("/search")
    @PreAuthorize(" isAuthenticated()")
    public ResponseEntity<ResponseBody<KakaoSearchResponse>> search(
            @RequestBody SearchRequest request,
            @Parameter(hidden = true) Long userId
    );
}
