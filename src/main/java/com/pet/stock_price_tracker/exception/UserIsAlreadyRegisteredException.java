package com.pet.stock_price_tracker.exception;

public class UserIsAlreadyRegisteredException extends RuntimeException {
    public UserIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
