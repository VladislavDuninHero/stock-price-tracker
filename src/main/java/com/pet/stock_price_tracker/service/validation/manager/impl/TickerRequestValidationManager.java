package com.pet.stock_price_tracker.service.validation.manager.impl;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.config.ValidationConfig;
import com.pet.stock_price_tracker.service.validation.manager.ValidationManager;
import org.springframework.stereotype.Service;

@Service
public class TickerRequestValidationManager implements ValidationManager<TickerRequestDTO> {

    private final ValidationConfig validationConfig;

    public TickerRequestValidationManager(ValidationConfig validationConfig) {
        this.validationConfig = validationConfig;
    }

    @Override
    public void validate(TickerRequestDTO value) {
        validationConfig.configureTickerRequestValidators().forEach(validator -> validator.validate(value));
    }

}
