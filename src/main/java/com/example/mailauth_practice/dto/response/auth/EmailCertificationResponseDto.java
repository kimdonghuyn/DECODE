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
public class EmailCertificationResponseDto extends ResponseDto {

    private EmailCertificationResponseDto() {
        super();
    }

    public static ResponseEntity<EmailCertificationResponseDto> success() {
        EmailCertificationResponseDto responseBody = new EmailCertificationResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicateId() {
        ResponseDto responseBody = new ResponseDto(ResponseCodeE.DUPLICATE_ID.getCode(), ResponseCodeMessageE.DUPLICATE_ID.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> mailSendFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCodeE.MAIL_FAIL.getCode(), ResponseCodeMessageE.MAIL_FAIL.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
