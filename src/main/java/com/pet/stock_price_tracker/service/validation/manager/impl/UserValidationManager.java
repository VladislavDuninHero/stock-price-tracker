package com.pet.stock_price_tracker.service.validation.manager.impl;

import com.pet.stock_price_tracker.config.ValidationConfig;
import com.pet.stock_price_tracker.dto.user.UserDTO;
import com.pet.stock_price_tracker.service.validation.manager.ValidationManager;
import org.springframework.stereotype.Service;

@Service
public class UserValidationManager implements ValidationManager<UserDTO> {

    private final ValidationConfig validationConfig;

    public UserValidationManager(ValidationConfig validationConfig) {
        this.validationConfig = validationConfig;
    }

    @Override
    public void validate(UserDTO value) {
        validationConfig.configurateUserValidators().forEach(validator -> validator.validate(value));
    }

}
