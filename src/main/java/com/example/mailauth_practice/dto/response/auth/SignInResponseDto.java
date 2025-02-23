package com.example.mailauth_practice.dto.response.auth;

import com.example.mailauth_practice.common.ResponseCodeE;
import com.example.mailauth_practice.common.ResponseCodeMessageE;
import com.example.mailauth_practice.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDto extends ResponseDto {

    private String token;
    private int expirationTime;

    private SignInResponseDto(String token) {
        super();
        this.token = token;
        this.expirationTime = 3600;
    }

    public static ResponseEntity<SignInResponseDto> success(String token) {
        SignInResponseDto responseBody = new SignInResponseDto(token);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCodeE.SIGN_IN_FAIL.getCode(), ResponseCodeMessageE.SIGN_IN_FAIL.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }


}
