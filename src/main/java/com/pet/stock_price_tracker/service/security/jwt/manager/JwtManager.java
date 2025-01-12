package com.pet.stock_price_tracker.service.security.jwt.manager;

import com.pet.stock_price_tracker.dto.security.JwtDTO;

public interface JwtManager {
    JwtDTO setupJwt(String login);
}
