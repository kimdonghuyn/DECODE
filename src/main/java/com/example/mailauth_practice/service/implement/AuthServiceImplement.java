package com.example.mailauth_practice.service.implement;

import com.example.mailauth_practice.common.CertificationNumber;
import com.example.mailauth_practice.dto.request.auth.*;
import com.example.mailauth_practice.dto.response.ResponseDto;
import com.example.mailauth_practice.dto.response.auth.*;
import com.example.mailauth_practice.entity.CertificationEntity;
import com.example.mailauth_practice.entity.UserEntity;
import com.example.mailauth_practice.provider.EmailProvider;
import com.example.mailauth_practice.provider.JwtProvider;
import com.example.mailauth_practice.provider.SmsProvider;
import com.example.mailauth_practice.repository.CertificationRepository;
import com.example.mailauth_practice.repository.UserRepository;
import com.example.mailauth_practice.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;

    private final CertificationRepository certificationRepository;

    private final JwtProvider jwtProvider;

    private final EmailProvider emailProvider;

    private final SmsProvider smsProvider;

    private final RedisTemplate<String, Object> redisTemplate;

    private final HttpSession session;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // 의존성 주입 x (어떤걸 선택할지 여기서 선택할거기 때문)


    /**
     * 아이디 중복 체크
     */
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


    /**
     * 이메일 인증코드 전송
     */
    @Override
    public ResponseEntity<? super CertificationResponseDto> emailCertification(CertificationRequestDto dto) {
        try {
            String userId = dto.getId();
            String email = dto.getEmail();

            boolean isExistId = userRepository.existsById(userId);   // 아이디 중복 체크
            if (isExistId) return CertificationResponseDto.duplicateId();

            String certificationNumber = CertificationNumber.getCertificationNumber();  // 인증 코드

            boolean isSuccess = emailProvider.sendCertificationMail(email, certificationNumber);
            if (!isSuccess) return CertificationResponseDto.mailSendFail();

            CertificationEntity certificationEntity = new CertificationEntity(userId, email, certificationNumber);
            certificationRepository.save(certificationEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CertificationResponseDto.success();
    }


    /**
     * 이메일 인증코드 인증
     */
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


    /**
     * 회원가입
     */
    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            String userId = dto.getId();

            boolean isExistId = userRepository.existsById(userId);
            if (isExistId) return SignUpResponseDto.duplicateId(); // 아이디가 존재하면

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


    /**
     * 로그인
     */
    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        String token = null;

        try {
            String userId = dto.getId();
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return SignInResponseDto.signInFail();

            String password = dto.getPassword();
            String encodedPassword = userEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) return SignInResponseDto.signInFail();

            token = jwtProvider.create(userId);

            session.setAttribute("userId", userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token);
    }


    /**
     * 문자 인증코드 전송
     */
    @Override
    public ResponseEntity<? super SendSmsResponseDto> sendSms(SendSmsRequestDto dto) {
        try {
            String phoneNumber = dto.getPhoneNumber();
            String certificationNumber = CertificationNumber.getCertificationNumber();
            long timeout = 5;

            boolean result = smsProvider.sendSms(phoneNumber, certificationNumber);
            if (!result) return SendSmsResponseDto.smsSendFail();

            redisTemplate.opsForValue().set(phoneNumber, certificationNumber, timeout, TimeUnit.MINUTES);
        } catch (Exception exception) {
            exception.printStackTrace();
            return SendSmsResponseDto.smsSendFail();
        }

        return SendSmsResponseDto.success();
    }

    /**
     * 문자 인증코드 인증
     */
    @Override
    public ResponseEntity<? super CheckSmsCertificationResponseDto> checkSmsCertification(CheckSmsCertificationRequestDto dto) {
        try {
            String phoneNumber = dto.getPhoneNumber();

            String inputCertificationNumber = dto.getCertificationNumber();

            String storedCertificationNumber = String.valueOf(redisTemplate.opsForValue().get(phoneNumber));

            System.out.println(storedCertificationNumber);

            System.out.println(phoneNumber + " / " + inputCertificationNumber);
            if (storedCertificationNumber == null || "".equals(storedCertificationNumber))
                return CheckSmsCertificationResponseDto.certificationFail();

            boolean isMatched = inputCertificationNumber.equals(storedCertificationNumber);

            if (!isMatched) return CheckSmsCertificationResponseDto.validationFail();

            redisTemplate.delete(phoneNumber);
        } catch (Exception exception) {
            exception.printStackTrace();
            return SendSmsResponseDto.databaseError();
        }

        return CheckSmsCertificationResponseDto.success();
    }
}
