package com.pet.stock_price_tracker.service.security.jwt.impl;

import com.pet.stock_price_tracker.entity.Permission;
import com.pet.stock_price_tracker.entity.Role;
import com.pet.stock_price_tracker.properties.JwtSecretProperties;
import com.pet.stock_price_tracker.service.security.jwt.JwtService;
import com.pet.stock_price_tracker.service.user.roles.RoleService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtSecretProperties jwtSecretProperties;
    private final RoleService roleService;

    public JwtServiceImpl(JwtSecretProperties jwtSecretProperties, RoleService roleService) {
        this.jwtSecretProperties = jwtSecretProperties;
        this.roleService = roleService;
    }

    @Override
    public String generateAccessToken(String login) {
        Map<String, Object> claims = new HashMap<>();

        Set<Permission> permissions = extractPermissions(roleService.getRoleForUser(login));
        List<String> authorities = permissions.stream().map(Permission::getAuthority).toList();

        claims.put("authorities", authorities);

        return Jwts.builder()
                .addClaims(claims)
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + jwtSecretProperties.getAccessExpirationDate()
                        )
                )
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

    @Override
    public Set<Permission> extractPermissions(List<Role> roles) {
        Set<Permission> permissions = new HashSet<>();

        for (Role role : roles) {
            permissions.addAll(role.getPermissions());
        }

        return permissions;
    }

    @Override
    public String generateRestorePasswordToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + jwtSecretProperties.getRestorePasswordExpirationDate()
                        )
                )
                .signWith(jwtSecretProperties.getSecretKey())
                .compact();
    }
}
