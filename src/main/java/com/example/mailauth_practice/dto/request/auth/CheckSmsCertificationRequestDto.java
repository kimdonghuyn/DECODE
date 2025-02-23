package com.example.mailauth_practice.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckSmsCertificationRequestDto {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String certificationNumber;
}
