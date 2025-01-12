package com.pet.stock_price_tracker.service.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtService {
    String generateAccessToken(String login);
    String generateRefreshToken(String login);
    Claims extractClaims(String token);
    boolean validateToken(String token, UserDetails userDetails);
    String refreshAccessToken(String refreshToken);
}
