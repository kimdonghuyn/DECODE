package com.example.mailauth_practice.service;

import com.example.mailauth_practice.dto.request.auth.CheckCertificationRequestDto;
import com.example.mailauth_practice.dto.request.auth.EmailCertificationRequestDto;
import com.example.mailauth_practice.dto.request.auth.IdCheckRequestDto;
import com.example.mailauth_practice.dto.request.auth.SignUpRequestDto;
import com.example.mailauth_practice.dto.response.auth.CheckCertificationResponseDto;
import com.example.mailauth_practice.dto.response.auth.EmailCertificationResponseDto;
import com.example.mailauth_practice.dto.response.auth.IdCheckResponseDto;
import com.example.mailauth_practice.dto.response.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    // 아이디 체크
    ResponseEntity<? super IdCheckResponseDto> IdCheck(IdCheckRequestDto dto);

    // 이메일 확인
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    // 인증 코드 확인
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);

    // 회원가입
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

}
