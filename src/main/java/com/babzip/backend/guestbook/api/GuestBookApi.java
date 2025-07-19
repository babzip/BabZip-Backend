package com.babzip.backend.guestbook.api;

import com.babzip.backend.global.aop.AssignUserId;
import com.babzip.backend.global.config.swagger.SwaggerApiFailedResponse;
import com.babzip.backend.global.config.swagger.SwaggerApiResponses;
import com.babzip.backend.global.config.swagger.SwaggerApiSuccessResponse;
import com.babzip.backend.global.exception.ExceptionType;
import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.global.response.ResponseUtil;
import com.babzip.backend.guestbook.dto.request.GuestbookRequestDto;
import com.babzip.backend.guestbook.dto.response.GuestbookResponseDto;
import com.babzip.backend.guestbook.dto.response.GuestbookSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "방명록 API", description = "방명록 관련 API")
public interface GuestBookApi {
    @Operation(
            summary = "방명록 작성",
            description = "사용자는 방명록을 작성합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "방명록 작성 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.USER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.GUEST_BOOK_NOT_FOUND),
            }
    )
    @AssignUserId
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> create(
            @Parameter(hidden = true) Long userId,
            @RequestBody GuestbookRequestDto requestDto
    );


    @Operation(
            summary = "방명록 페이징 조회",
            description = "사용자는 페이지로 작성한 방명록을 조회할 수 있습니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = GuestbookResponseDto.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    responsePage = GuestbookResponseDto.class,
                    description = "방명록 페이징 조회 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.USER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.GUEST_BOOK_NOT_FOUND),
            }
    )
    @AssignUserId
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Page<GuestbookResponseDto>>> getMyGuestbooks(
            @Parameter(hidden = true) Long userId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    );


    @Operation(
            summary = "방명록 수정",
            description = "사용자는 방명록을 수정할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "방명록 수정 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.USER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.GUEST_BOOK_NOT_FOUND),
            }
    )
    @AssignUserId
    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> updatePartial(
            @Parameter(hidden = true) Long userId,
            @RequestBody GuestbookRequestDto requestDto
    );


    @Operation(
            summary = "방명록 삭제",
            description = "사용자는 방명록을 삭제할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "방명록 삭제 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.USER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.GUEST_BOOK_NOT_FOUND),
            }
    )
    @AssignUserId
    @DeleteMapping("/{kakaoPlaceId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> delete(
            @Parameter(hidden = true) Long userId,
            @PathVariable String kakaoPlaceId
    );

    @Operation(
            summary = "방명록 검색",
            description = "사용자는 마이페이지에서 방명록을 검색할 수 있습니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = GuestbookSearchResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    responsePage = GuestbookSearchResponse.class,
                    description = "방명록 검색 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED)
            }
    )
    @AssignUserId
    @GetMapping("/search/{query}")
    public ResponseEntity<ResponseBody<Page<GuestbookSearchResponse>>> search(
            @Parameter(hidden = true) Long userId,
            @PathVariable String query,
            @ParameterObject
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    );
}
