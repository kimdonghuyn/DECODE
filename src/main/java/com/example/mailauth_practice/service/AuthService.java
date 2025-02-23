package com.example.mailauth_practice.service;

import com.example.mailauth_practice.dto.request.auth.*;
import com.example.mailauth_practice.dto.response.auth.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    // 아이디 체크
    ResponseEntity<? super IdCheckResponseDto> IdCheck(IdCheckRequestDto dto);

    // 이메일 확인
    ResponseEntity<? super CertificationResponseDto> emailCertification(CertificationRequestDto dto);

    // 이메일 인증 코드 확인
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);

    // 문자 인증코드 전송
    ResponseEntity<? super SendSmsResponseDto> sendSms(SendSmsRequestDto dto);

    // 문자 인증코드 확인
    ResponseEntity<? super CheckSmsCertificationResponseDto> checkSmsCertification(CheckSmsCertificationRequestDto dto);

    // 회원가입
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    // 로그인
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
