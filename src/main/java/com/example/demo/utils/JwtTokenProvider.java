package com.example.demo.utils;

import com.example.demo.models.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${jwt.expiration}")
    private int expiration;

    private static final String SECRET = "lQWsFF+SWnbZM7iH1qZjCTHGEpvcShZ1Zw+jNEpDkbg=";


    long expirationMillis = 3600 * 1000;

    public String generateToken(UserEntity user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);
        String token = null;
        token = Jwts.builder()
            .setId(String.valueOf(user.getId()))
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
        return token;
    }


}
