package com.example.demo.config;

public enum ResultCode implements IErrorCode {
    SUCCESS(200, "success"),
    FAILED(500, "failed."),
    SYSTEM_INNER_ERROR(201, "system error."),
    OAUTH_NO_ACCESS(401, "Unauthorised"),
    NO_TOKEN_PRESENT(401, "User is not logged in"),

    ;

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
