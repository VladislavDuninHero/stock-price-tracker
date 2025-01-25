package com.pet.stock_price_tracker.service.validation.manager.impl;

import com.pet.stock_price_tracker.config.ValidationConfig;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.service.validation.manager.ValidationManager;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationValidationManager implements ValidationManager<UserDTO> {

    private final ValidationConfig validationConfig;

    public UserRegistrationValidationManager(ValidationConfig validationConfig) {
        this.validationConfig = validationConfig;
    }

    @Override
    public void validate(UserDTO value) {
        validationConfig.configureUserValidators().forEach(validator -> validator.validate(value));
    }

}
