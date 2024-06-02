package com.example.mailauth_practice.common;


// 인증 코드 만들기
public class CertificationNumber {
    public static String getCertificationNumber() {

        StringBuilder certificationNumber = new StringBuilder();

        for (int count = 0; count < 4; count++) {
            certificationNumber.append((int) (Math.random() * 10));
        }

        return certificationNumber.toString();
    }
}
