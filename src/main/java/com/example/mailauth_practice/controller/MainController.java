package com.example.mailauth_practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/")
    public String main() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signup")
    public String signup() {
        return "signup";
    }

    @RequestMapping("/find-id")
    public String findId() {
        return "find-id";
    }

    @RequestMapping("/find-pw")
    public String findPw() {
        return "find-pw";
    }

    @RequestMapping("/change-pw")
    public String changePw() {
        return "change-pw";
    }

    @RequestMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    @RequestMapping("/user-info")
    public String userInfo() {
        return "user-info";
    }

    @RequestMapping("/user-info-change")
    public String userInfoChange() {
        return "user-info-change";
    }

    @RequestMapping("/user-info-change-pw")
    public String userInfoChangePw() {
        return "user-info-change-pw";
    }

    @RequestMapping("/user-info-change-email")
    public String userInfoChangeEmail() {
        return "user-info-change-email";
    }

    @RequestMapping("/user-info-change-phone")
    public String userInfoChangePhone() {
        return "user-info-change-phone";
    }

    @RequestMapping("/user-info-change-address")
    public String userInfoChangeAddress() {
        return "user-info-change-address";
    }

    @RequestMapping("/user-info-change-birth")
    public String userInfoChangeBirth() {
        return "user-info-change-birth";
    }
}
