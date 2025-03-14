package com.example.mailauth_practice.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CertificationRequestDto {

    @NotBlank
    private String id;

    @Email
    @NotBlank
    private String email;
}
