package com.example.mailauth_practice.common;

public enum ResponseCodeE {
    SUCCESS("SU"),
    VALIDATION_FAIL("VF"),
    DUPLICATE_ID("DI"),
    SIGN_IN_FAIL("SF"),
    CERTIFICATION_FAIL("CF"),
    MAIL_FAIL("MF"),
    SMS_FAIL("SMF"),
    DATABASE_ERROR("DBE");

    private final String code;

    ResponseCodeE(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
