package com.pet.stock_price_tracker.exception;

public class DateValidationException extends RuntimeException {
    public DateValidationException(String message) {
        super(message);
    }
}
