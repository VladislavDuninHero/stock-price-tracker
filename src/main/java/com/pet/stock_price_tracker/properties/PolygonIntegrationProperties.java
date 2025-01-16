package com.pet.stock_price_tracker.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@PropertySource("classpath:application.properties")
public class PolygonIntegrationProperties {

    @Value("${feign.integration.polygon.api-key}")
    private String apiKey;
}
