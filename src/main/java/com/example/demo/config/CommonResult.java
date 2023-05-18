package com.example.demo.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    /**
     * code
     */
    private long code;

    /**
     * message
     */
    private String message;


    /**
     * Generic data
     */
    private T data;
    private Boolean success;

    //If i just want the default message to be displayed
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),  data, true);
    }

    //If i want to include a success message
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data, true);
    }

    //
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(),  null, false);
    }

    //If i want to specify the error code and error message
    public static <T> CommonResult<T> failed(Integer errorCode, String message) {
        return new CommonResult<T>(errorCode, message, null, false);
    }

    //If i want to specify an error message
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null, false);
    }
}
