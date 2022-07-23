package com.fintech.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.keysecret}")
    private String JWT_SECRET;

    @Getter
    @Value("${jwt.expiration.time}")
    private Long jwtExpiration;

    public String generateTokenWithUserName(String username) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .claim("scope", "ROLE_USER")
                .compact();

    }
}
