package com.example.mailauth_practice.common;

public enum ResponseCodeMessageE {
    SUCCESS("Success."),
    VALIDATION_FAIL("Validation failed."),
    DUPLICATE_ID("Duplicate Id."),
    SIGN_IN_FAIL("Login information mismatch."),
    CERTIFICATION_FAIL("Certification failed."),
    MAIL_FAIL("Mail send failed."),
    DATABASE_ERROR("Database error.");

    private final String message;

    ResponseCodeMessageE(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
