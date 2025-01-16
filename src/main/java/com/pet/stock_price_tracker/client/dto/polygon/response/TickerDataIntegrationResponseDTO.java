package com.pet.stock_price_tracker.client.dto.polygon.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TickerDataIntegrationResponseDTO {
    private BigDecimal o;
    private BigDecimal c;
    private BigDecimal h;
    private BigDecimal l;
    private Timestamp t;
}
