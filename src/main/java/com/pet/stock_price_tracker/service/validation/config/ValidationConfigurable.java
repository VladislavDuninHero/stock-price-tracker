package com.pet.stock_price_tracker.service.validation.config;

import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;

import java.util.List;

public interface ValidationConfigurable {
    <T> List<BaseValidator<T>> config(List<BaseValidator<T>> validators);
}
