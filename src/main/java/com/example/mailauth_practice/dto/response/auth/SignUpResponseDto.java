package com.example.mailauth_practice.dto.response.auth;

import com.example.mailauth_practice.common.ResponseCode;
import com.example.mailauth_practice.common.ResponseCodeE;
import com.example.mailauth_practice.common.ResponseCodeMessage;
import com.example.mailauth_practice.common.ResponseCodeMessageE;
import com.example.mailauth_practice.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignUpResponseDto extends ResponseDto {

    private SignUpResponseDto() {
        super();
    }

    public static ResponseEntity<SignUpResponseDto> success() {
        SignUpResponseDto responseBody = new SignUpResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicateId() {
        ResponseDto responseBody = new ResponseDto(ResponseCodeE.DUPLICATE_ID.getCode(), ResponseCodeMessageE.DUPLICATE_ID.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> certificationFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCodeE.CERTIFICATION_FAIL.getCode(), ResponseCodeMessageE.CERTIFICATION_FAIL.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
