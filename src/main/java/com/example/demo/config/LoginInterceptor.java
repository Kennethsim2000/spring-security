package com.example.demo.config;

import com.example.demo.dto.UserDto;
import com.example.demo.utils.JwtTokenProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;


@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String SECRET = "lQWsFF+SWnbZM7iH1qZjCTHGEpvcShZ1Zw+jNEpDkbg=";

    ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String editId = request.getHeader("id");
        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        String token = getToken(request);
        log.info("token:{}", token);
        if (StringUtils.isEmpty(token)) {
            this.doFailed(response, HttpStatus.UNAUTHORIZED.value(), ResultCode.NO_TOKEN_PRESENT.getMessage());
            return false;
        }
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = claims.getId();
        if (StringUtils.isEmpty(id)) {
            this.doFailed(response, HttpStatus.UNAUTHORIZED.value(), ResultCode.OAUTH_NO_ACCESS.getMessage());
            return false;
        }
        if (!id.equals(editId)) {
            this.doFailed(response, HttpStatus.UNAUTHORIZED.value(), ResultCode.OAUTH_NO_ACCESS.getMessage());
            return false;
        }
        System.out.println("token is authorised");
        return true;
    }
    private void doFailed(HttpServletResponse response, int httpStatus, String msg) throws IOException {
        response.setStatus(httpStatus);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Headers", "*");
        PrintWriter pw = response.getWriter();
        CommonResult<Boolean> result =CommonResult.failed(httpStatus,msg);
        pw.write(objectMapper.writeValueAsString(result));
        pw.flush();
        pw.close();
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX))
        {
            token = token.replace(TOKEN_PREFIX, "");
        }
        return token;
    }
}
