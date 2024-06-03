package com.example.mailauth_practice.service.implement;

import com.example.mailauth_practice.common.CertificationNumber;
import com.example.mailauth_practice.dto.request.auth.CheckCertificationRequestDto;
import com.example.mailauth_practice.dto.request.auth.EmailCertificationRequestDto;
import com.example.mailauth_practice.dto.request.auth.IdCheckRequestDto;
import com.example.mailauth_practice.dto.request.auth.SignUpRequestDto;
import com.example.mailauth_practice.dto.response.ResponseDto;
import com.example.mailauth_practice.dto.response.auth.CheckCertificationResponseDto;
import com.example.mailauth_practice.dto.response.auth.EmailCertificationResponseDto;
import com.example.mailauth_practice.dto.response.auth.IdCheckResponseDto;
import com.example.mailauth_practice.dto.response.auth.SignUpResponseDto;
import com.example.mailauth_practice.entity.CertificationEntity;
import com.example.mailauth_practice.entity.UserEntity;
import com.example.mailauth_practice.provider.EmailProvider;
import com.example.mailauth_practice.repository.CertificationRepository;
import com.example.mailauth_practice.repository.UserRepository;
import com.example.mailauth_practice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;

    private final CertificationRepository certificationRepository;

    private final EmailProvider emailProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // 의존성 주입 x (어떤걸 선택할지 여기서 선택할거기 때문)

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

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
        try {
            String userId = dto.getId();
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            if (certificationEntity == null) return CheckCertificationResponseDto.certificationFail();  // 존재 x

            boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) return CheckCertificationResponseDto.certificationFail();  // 해당 데이터 일치 x

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CheckCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            String userId = dto.getId();

            boolean isExistId = userRepository.existsById(userId);
            if(isExistId) return SignUpResponseDto.duplicateId(); // 아이디가 존재하면

            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            // 이메일과 인증 코드가 일치하는지 확인
            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) return SignUpResponseDto.certificationFail();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

            // certificationRepository.delete(certificationEntity);   이거 써도됨
            certificationRepository.deleteByUserId(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }
}
