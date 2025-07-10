package com.babzip.backend.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/hello")
    @PreAuthorize(" isAuthenticated() and hasAuthority('USER')")
    public String hello(){
        return "Hello World";
    }

}
