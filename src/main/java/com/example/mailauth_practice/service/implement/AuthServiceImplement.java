package com.example.mailauth_practice.service.implement;

import com.example.mailauth_practice.common.CertificationNumber;
import com.example.mailauth_practice.dto.request.auth.EmailCertificationRequestDto;
import com.example.mailauth_practice.dto.request.auth.IdCheckRequestDto;
import com.example.mailauth_practice.dto.response.ResponseDto;
import com.example.mailauth_practice.dto.response.auth.EmailCertificationResponseDto;
import com.example.mailauth_practice.dto.response.auth.IdCheckResponseDto;
import com.example.mailauth_practice.entity.CertificationEntity;
import com.example.mailauth_practice.provider.EmailProvider;
import com.example.mailauth_practice.repository.CertificationRepository;
import com.example.mailauth_practice.repository.UserRepository;
import com.example.mailauth_practice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;

    private final CertificationRepository certificationRepository;

    private final EmailProvider emailProvider;

    @Override
    public ResponseEntity<? super IdCheckResponseDto> IdCheck(IdCheckRequestDto dto) {
        try {
            String userId = dto.getId();
            boolean isExistId = userRepository.existsByUserId(userId);

            if (isExistId) return IdCheckResponseDto.duplicateId();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        try {
            String userId = dto.getId();
            String email = dto.getEmail();

            boolean isExistId = userRepository.existsById(userId);   // 아이디 중복 체크
            if (isExistId) return EmailCertificationResponseDto.duplicateId();

            String certificationNumber = CertificationNumber.getCertificationNumber();  // 인증 코드

            boolean isSuccess = emailProvider.sendCertificationMail(email, certificationNumber);
            if(!isSuccess) return EmailCertificationResponseDto.mailSendFail();

            CertificationEntity certificationEntity = new CertificationEntity(userId, email, certificationNumber);
            certificationRepository.save(certificationEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return EmailCertificationResponseDto.success();
    }
}
