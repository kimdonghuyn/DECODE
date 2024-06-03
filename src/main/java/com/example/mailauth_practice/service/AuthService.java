package com.example.mailauth_practice.service;

import com.example.mailauth_practice.dto.request.auth.CheckCertificationRequestDto;
import com.example.mailauth_practice.dto.request.auth.EmailCertificationRequestDto;
import com.example.mailauth_practice.dto.request.auth.IdCheckRequestDto;
import com.example.mailauth_practice.dto.response.auth.CheckCertificationResponseDto;
import com.example.mailauth_practice.dto.response.auth.EmailCertificationResponseDto;
import com.example.mailauth_practice.dto.response.auth.IdCheckResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<? super IdCheckResponseDto> IdCheck(IdCheckRequestDto dto);

    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);
}
