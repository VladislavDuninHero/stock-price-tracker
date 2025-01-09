package com.pet.stock_price_tracker.service.security.jwt.impl;

import com.pet.stock_price_tracker.properties.JwtSecretProperties;
import com.pet.stock_price_tracker.service.security.jwt.JwtService;


public class JwtServiceImpl implements JwtService {

    private final JwtSecretProperties jwtSecretProperties;

    public JwtServiceImpl(JwtSecretProperties jwtSecretProperties) {
        this.jwtSecretProperties = jwtSecretProperties;
    }


}
