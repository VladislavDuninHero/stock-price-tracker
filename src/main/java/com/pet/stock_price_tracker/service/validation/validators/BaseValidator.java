package com.pet.stock_price_tracker.service.validation.validators;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseValidator<T> implements Validator<T> {
    Validator<T> next;
}
