package com.babzip.backend.user.controller;

import com.babzip.backend.global.aop.AssignUserId;
import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.user.api.UserApi;
import com.babzip.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.babzip.backend.global.response.ResponseUtil.createSuccessResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @AssignUserId
    @DeleteMapping("/logout")
    @PreAuthorize(" isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<ResponseBody<Void>> logout(Long userId){
        userService.logout(userId);
        return ResponseEntity.ok(createSuccessResponse());
    }

}
