package com.pet.stock_price_tracker.exception;

public class InvalidCharactersException extends RuntimeException {
    public InvalidCharactersException(String message) {
        super(message);
    }
}
