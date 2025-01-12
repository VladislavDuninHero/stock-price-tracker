package com.pet.stock_price_tracker.properties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Getter
@Setter
@Component
@PropertySource("classpath:application.properties")
public class JwtSecretProperties {

    @Value("${jwt.secret}")
    private String secret;

    private final Integer accessExpirationDate = 60 * 60 * 1000;

    private final Integer refreshExpirationDate = 2 * 7 * 24 * 60 * 60 * 1000;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}