package com.pet.stock_price_tracker.service.validation.validators;

public interface Validator<T> {
    T validate(T value);
}
