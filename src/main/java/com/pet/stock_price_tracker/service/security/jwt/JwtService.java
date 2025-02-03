package com.pet.stock_price_tracker.service.security.jwt;

import com.pet.stock_price_tracker.entity.Permission;
import com.pet.stock_price_tracker.entity.Role;
import io.jsonwebtoken.Claims;

import java.util.List;
import java.util.Set;

public interface JwtService {
    String generateAccessToken(String login);
    String generateRefreshToken(String login);
    Claims validateToken(String token);
    String refreshAccessToken(String refreshToken);
    String getLoginFromToken(String token);
    Set<Permission> extractPermissions(List<Role> roles);
}
