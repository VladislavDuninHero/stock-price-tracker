package com.pet.stock_price_tracker.service.security.jwt.impl;

import com.pet.stock_price_tracker.properties.JwtSecretProperties;
import com.pet.stock_price_tracker.service.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtSecretProperties jwtSecretProperties;

    public JwtServiceImpl(JwtSecretProperties jwtSecretProperties) {
        this.jwtSecretProperties = jwtSecretProperties;
    }

    @Override
    public String generateAccessToken(String login) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .addClaims(claims)
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtSecretProperties.getAccessExpirationDate()))
                .signWith(jwtSecretProperties.getSecretKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtSecretProperties.getRefreshExpirationDate()))
                .signWith(jwtSecretProperties.getSecretKey())
                .compact();
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        String login = extractClaims(token).getSubject();

        return userDetails.getUsername().equals(login);
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        Claims claims = extractClaims(refreshToken);
        String login = claims.getSubject();

        return generateAccessToken(login);
    }
}