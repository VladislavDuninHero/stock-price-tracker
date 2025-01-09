package com.pet.stock_price_tracker.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:secrets.yaml")
public class JwtSecretProperties {

    @Value("${jwt.secret}")
    private String key;
}
