package com.pet.stock_price_tracker.exception;

public class TickerNotFoundException extends RuntimeException {
    public TickerNotFoundException(String message) {
        super(message);
    }
}
