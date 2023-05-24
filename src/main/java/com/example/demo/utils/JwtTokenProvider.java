package com.example.demo.utils;

import com.example.demo.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${jwt.expiration}")
    private int expiration;

    private static final String SECRET = "mySecret";


    long expirationMillis = 3600 * 1000;
    Date expirationDate = new Date(System.currentTimeMillis() + expirationMillis);
    private static Key SECRET_KEY ;
    private ObjectMapper objectMapper=new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public String generateToken(User user, Key key) {
        SECRET_KEY = key;
        String token = null;
        token = Jwts.builder()
            .setId(String.valueOf(user.getId()))
            .setIssuedAt(new Date())
            .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
        return token;
    }

    public long validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            String id = claims.getId();
            return Long.parseLong(id);
        } catch (JwtException e) {
            // Token validation failed
            // Handle the exception or return an appropriate value
            log.info(e.getMessage());
            return -1;
        }
    }

}
