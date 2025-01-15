package com.pet.stock_price_tracker.client.dto.polygon.request;

import java.time.LocalDate;

public class TickerFormattedRequestDTO {
    private int multiplier;
    private String ticker;
    private String day;
    private LocalDate from;
    private LocalDate to;
}
