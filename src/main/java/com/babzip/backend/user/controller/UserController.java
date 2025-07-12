package com.babzip.backend.user.controller;

import com.babzip.backend.global.aop.AssignUserId;
import com.babzip.backend.global.oauth.service.OAuth2UserPrincipal;
import com.babzip.backend.global.response.ResponseBody;
import com.babzip.backend.global.response.ResponseUtil;
import com.babzip.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.babzip.backend.global.response.ResponseUtil.createSuccessResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    @PreAuthorize(" isAuthenticated() and hasAuthority('USER')")
    public String hello(){
        return "Hello World";
    }

    @AssignUserId
    @DeleteMapping("/logout")
    @PreAuthorize(" isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<ResponseBody<Void>> logout(Long userId){
        userService.logout(userId);
        return ResponseEntity.ok(createSuccessResponse());
    }

}
