package com.pet.stock_price_tracker.service.validation.manager.impl;

import com.pet.stock_price_tracker.config.ValidationConfig;
import com.pet.stock_price_tracker.dto.user.login.UserLoginDTO;
import com.pet.stock_price_tracker.service.validation.manager.ValidationManager;
import com.pet.stock_price_tracker.service.validation.validators.Validator;
import org.springframework.stereotype.Service;

@Service
public class UserLoginValidationManager implements ValidationManager<UserLoginDTO> {

    private final ValidationConfig validationConfig;

    public UserLoginValidationManager(ValidationConfig validationConfig) {
        this.validationConfig = validationConfig;
    }

    @Override
    public void validate(UserLoginDTO value) {
        validationConfig.configurateUserLoginValidators().forEach(validator -> validator.validate(value));
    }


}
