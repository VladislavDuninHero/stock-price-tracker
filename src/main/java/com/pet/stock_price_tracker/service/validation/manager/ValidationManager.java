package com.pet.stock_price_tracker.service.validation.manager;

public interface ValidationManager<T> {
    void validate(T value);
}
