package com.babzip.backend.top10.api;

import com.babzip.backend.global.aop.AssignUserId;
import com.babzip.backend.global.config.swagger.SwaggerApiFailedResponse;
import com.babzip.backend.global.config.swagger.SwaggerApiResponses;
import com.babzip.backend.global.config.swagger.SwaggerApiSuccessResponse;
import com.babzip.backend.global.exception.ExceptionType;
import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.search.dto.response.KakaoSearchResponse;
import com.babzip.backend.top10.dto.request.Top10Request;
import com.babzip.backend.top10.dto.response.Top10Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Top10 API", description = "Top10 관련 API")
public interface Top10Api {
    @Operation(
            summary = "Top10 등록 및 수정",
            description = "사용자는 Top10을 등록 및 수정할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "Top10 등록, 수정 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.TOP10_LIMIT_EXCEEDED)
            }
    )
    @AssignUserId
    @PostMapping("/top10")
    @PreAuthorize(" isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> createTop10(
            @Parameter(hidden = true) Long userId,
            @RequestBody List<Top10Request> request
    );


    @Operation(
            summary = "Top10 조회",
            description = "사용자는 Top10을 Rank의 오름차순으로 조회할 수 있습니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = Top10Response.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    responsePage = Top10Response.class,
                    description = "Top10 조회 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED)
            }
    )
    @AssignUserId
    @PostMapping("/top10")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<ResponseBody<Page<Top10Response>>> getTop10(
            @Parameter(hidden = true) Long userId,
            @PageableDefault(sort = "rank", direction = Sort.Direction.ASC) Pageable pageable
    );
}
