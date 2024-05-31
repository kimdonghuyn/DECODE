package com.example.mailauth_practice.controller;

import com.example.mailauth_practice.dto.request.auth.IdCheckRequestDto;
import com.example.mailauth_practice.dto.response.auth.IdCheckResponseDto;
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
}
