package com.example.mailauth_practice.dto.response.auth;

import com.example.mailauth_practice.common.ResponseCodeE;
import com.example.mailauth_practice.common.ResponseCodeMessageE;
import com.example.mailauth_practice.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SendSmsResponseDto extends ResponseDto {
    private SendSmsResponseDto() {
        super();
    }

    public static ResponseEntity<SendSmsResponseDto> success() {
        SendSmsResponseDto responseBody = new SendSmsResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> smsSendFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCodeE.SMS_FAIL.getCode(), ResponseCodeMessageE.SMS_FAIL.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
