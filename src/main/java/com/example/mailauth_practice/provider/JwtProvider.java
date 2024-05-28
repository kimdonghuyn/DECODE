package com.example.mailauth_practice.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${secret-key}")  // application.property에 있는 secret-key를 가져옴
    private String secretKey;

    public String create(String userId) {

        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); // 유효기간 1시간으로 설정
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); // secretKey 만들기 (jwt의 마지막 부분에 들어감)

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256). // HEADER
                setSubject(userId).setIssuedAt(new Date()).setExpiration(expiredDate) // PAYLOAD
                .compact();

        return jwt;
    }


    // jwt 검증
    public String validate(String jwt) {
        String subject = null;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        return subject;
    }
}
