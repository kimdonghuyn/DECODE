package com.example.mailauth_practice.dto.response;

import com.example.mailauth_practice.common.ResponseCode;
import com.example.mailauth_practice.common.ResponseCodeE;
import com.example.mailauth_practice.common.ResponseCodeMessage;
import com.example.mailauth_practice.common.ResponseCodeMessageE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private String code;
    private String message;

    public ResponseDto() {
        // 인터페이스 사용
        /*this.code = ResponseCodeE.SUCCESS;
        this.message = ResponseCodeMessage.SUCCESS;*/

        // enum 사용
        this.code = ResponseCodeE.SUCCESS.getCode();
        this.message = ResponseCodeMessageE.SUCCESS.getMessage();

    }

    // Http Status - 500 (Internal Server Error)
    public static ResponseEntity<ResponseDto> databaseError() {
        /*ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseCodeMessage.DATABASE_ERROR);*/
        ResponseDto responseBody = new ResponseDto(ResponseCodeE.DATABASE_ERROR.getCode(), ResponseCodeMessageE.DATABASE_ERROR.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    // Http Status - 400 (Bad Request)
    public static ResponseEntity<ResponseDto> validationFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCodeE.VALIDATION_FAIL.getCode(), ResponseCodeMessageE.VALIDATION_FAIL.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
