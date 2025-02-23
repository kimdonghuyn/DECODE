package com.example.mailauth_practice.dto.response.auth;

import com.example.mailauth_practice.common.ResponseCodeE;
import com.example.mailauth_practice.common.ResponseCodeMessageE;
import com.example.mailauth_practice.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CheckSmsCertificationResponseDto extends ResponseDto {
    private CheckSmsCertificationResponseDto() {
        super();
    }

    public static ResponseEntity<CheckSmsCertificationResponseDto> success() {
        CheckSmsCertificationResponseDto responseBody = new CheckSmsCertificationResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> certificationFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCodeE.CERTIFICATION_FAIL.getCode(), ResponseCodeMessageE.CERTIFICATION_FAIL.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
