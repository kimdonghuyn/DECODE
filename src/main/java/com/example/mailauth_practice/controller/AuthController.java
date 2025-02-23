package com.example.mailauth_practice.controller;

import com.example.mailauth_practice.dto.request.auth.*;
import com.example.mailauth_practice.dto.response.auth.*;
import com.example.mailauth_practice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckResponseDto> idCheck(@RequestBody @Valid IdCheckRequestDto requestBody) {
        ResponseEntity<? super IdCheckResponseDto> response = authService.IdCheck(requestBody);

        return response;
    }

    @PostMapping("/email-certification")
    public ResponseEntity<? super CertificationResponseDto> emailCertification(@RequestBody @Valid CertificationRequestDto requestBody) {
        ResponseEntity<? super CertificationResponseDto> response = authService.emailCertification(requestBody);

        return response;
    }

    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(@RequestBody @Valid CheckCertificationRequestDto requestBody) {
        ResponseEntity<? super CheckCertificationResponseDto> response = authService.checkCertification(requestBody);
        return response;
    }

    @PostMapping("/sms-certification")
    public ResponseEntity<? super SendSmsResponseDto> sendSms(@RequestBody @Valid SendSmsRequestDto requestBody) {
        ResponseEntity<? super SendSmsResponseDto> response = authService.sendSms(requestBody);
        return response;
    }

    @PostMapping("/check-sms-certification")
    public ResponseEntity<? super CheckSmsCertificationResponseDto> checkSmsCertification(@RequestBody @Valid CheckSmsCertificationRequestDto requestBody) {
        ResponseEntity<? super CheckSmsCertificationResponseDto> response = authService.checkSmsCertification(requestBody);
        return response;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody);
        return response;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response;
    }
}
