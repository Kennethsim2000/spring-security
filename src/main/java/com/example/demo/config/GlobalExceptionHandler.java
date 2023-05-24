package com.example.demo.config;

import com.example.demo.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(UserNotFoundException.class)
    public CommonResult resolveException(Exception ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.error("User does not exist", ex);
        CommonResult result = CommonResult.failed(ResultCode.SYSTEM_INNER_ERROR.getCode(), ResultCode.SYSTEM_INNER_ERROR.getMessage());
        request.setAttribute("response", result);
        return result;
    }


}
