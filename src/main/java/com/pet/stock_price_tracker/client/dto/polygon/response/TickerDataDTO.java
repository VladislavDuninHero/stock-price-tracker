package com.pet.stock_price_tracker.client.dto.polygon.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TickerDataDTO {
    private LocalDate date;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal highest;
    private BigDecimal lowest;
}
