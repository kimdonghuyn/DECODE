package com.example.mailauth_practice.provider;

import com.example.mailauth_practice.common.CertificationNumber;
import com.example.mailauth_practice.dto.request.auth.CertificationRequestDto;
import com.example.mailauth_practice.dto.request.auth.SendSmsRequestDto;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsProvider {

    @Value("${sms.from-number}")
    String FROM;

    private final DefaultMessageService messageService;

    public SmsProvider(@Value("${sms.api-key}") String API_KEY,
                       @Value("${sms.api-secret-key}") String API_SECRET_KEY,
                       @Value("${sms.domain}") String DOMAIN) {
        this.messageService = NurigoApp.INSTANCE.initialize(API_KEY, API_SECRET_KEY, DOMAIN);
    }

    

    public boolean sendSms(String phoneNumber, String certificationNumber) {

        Message message = new Message();
        message.setFrom(FROM); // 발신번호
        message.setTo(phoneNumber);  // 수신번호
        message.setText("[DECODE] 인증번호 " + certificationNumber + "를 입력해주세요.");  // 메세지 내용

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));

        String statusCode = response.getStatusCode();
        boolean result = statusCode.equals("2000");

        return result;
    }
}
