package com.pet.stock_price_tracker.service.security.jwt.impl;

import com.pet.stock_price_tracker.entity.Permission;
import com.pet.stock_price_tracker.entity.Role;
import com.pet.stock_price_tracker.properties.JwtSecretProperties;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.security.jwt.JwtService;
import com.pet.stock_price_tracker.service.user.UserService;
import com.pet.stock_price_tracker.service.user.roles.RoleService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        Claims claims = validateToken(refreshToken);
        String login = claims.getSubject();

        return generateAccessToken(login);
    }

    @Override
    public String getLoginFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
