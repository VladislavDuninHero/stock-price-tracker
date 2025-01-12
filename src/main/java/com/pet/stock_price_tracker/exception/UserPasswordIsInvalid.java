package com.pet.stock_price_tracker.exception;

public class UserPasswordIsInvalid extends RuntimeException {
    public UserPasswordIsInvalid(String message) {
        super(message);
    }
}
