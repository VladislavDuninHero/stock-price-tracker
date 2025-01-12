package com.pet.stock_price_tracker.service.validation.config.impl;

import com.pet.stock_price_tracker.service.validation.config.ValidationConfigurable;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidatorsConfigurator implements ValidationConfigurable {

    @Override
    public <T> List<BaseValidator<T>> config(List<BaseValidator<T>> validators) {
        for (int i = 1; i < validators.size(); i++) {
            validators.get(i - 1).setNext(validators.get(i));
        }

        return validators;
    }
}
