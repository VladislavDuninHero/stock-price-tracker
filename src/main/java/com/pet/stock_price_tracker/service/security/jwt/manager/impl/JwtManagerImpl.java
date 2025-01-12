package com.pet.stock_price_tracker.service.security.jwt.manager.impl;

import com.pet.stock_price_tracker.dto.security.JwtDTO;
import com.pet.stock_price_tracker.service.security.jwt.JwtService;
import com.pet.stock_price_tracker.service.security.jwt.manager.JwtManager;
import org.springframework.stereotype.Service;

@Service
public class JwtManagerImpl implements JwtManager {

    private final JwtService jwtService;

    public JwtManagerImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public JwtDTO setupJwt(String login) {
        return new JwtDTO(
                jwtService.generateAccessToken(login),
                jwtService.generateRefreshToken(login)
        );
    }
}
