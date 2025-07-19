package com.babzip.backend.user.api;

import com.babzip.backend.global.aop.AssignUserId;
import com.babzip.backend.global.config.swagger.SwaggerApiFailedResponse;
import com.babzip.backend.global.config.swagger.SwaggerApiResponses;
import com.babzip.backend.global.config.swagger.SwaggerApiSuccessResponse;
import com.babzip.backend.global.exception.ExceptionType;
import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.guestbook.dto.response.GuestbookResponseDto;
import com.babzip.backend.user.dto.response.UserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


import static com.babzip.backend.global.response.ResponseUtil.createSuccessResponse;

@Tag(name = "사용자 API", description = "사용자 관련 API")
public interface UserApi {

    @Operation(
            summary = "로그아웃",
            description = "사용자는 로그아웃을 진행합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "로그아웃 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.USER_NOT_FOUND),
            }
    )
    @AssignUserId
    @DeleteMapping("/logout")
    @PreAuthorize(" isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<ResponseBody<Void>> logout(@Parameter(hidden = true) Long userId);



    @Operation(
            summary = "프로필 조회",
            description = "사용자는 자신의 프로필을 조회합니다."
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = UserProfileResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    response = UserProfileResponse.class,
                    description = "프로필 조회 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.USER_NOT_FOUND),
            }
    )
    @AssignUserId
    @GetMapping("/me")
    @PreAuthorize(" isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<ResponseBody<UserProfileResponse>> getMyProfile(
            @Parameter(hidden = true) Long userId
    );
}
