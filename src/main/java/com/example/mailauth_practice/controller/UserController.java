package com.example.mailauth_practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Test
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/aa")
    public String aa() {
        return "aa";
    }

}
