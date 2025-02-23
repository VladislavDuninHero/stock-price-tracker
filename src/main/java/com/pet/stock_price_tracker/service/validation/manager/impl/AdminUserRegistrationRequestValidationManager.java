package com.pet.stock_price_tracker.service.validation.manager.impl;

import com.pet.stock_price_tracker.config.ValidationConfig;
import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.service.validation.manager.ValidationManager;
import org.springframework.stereotype.Service;

@Service
public class AdminUserRegistrationRequestValidationManager implements ValidationManager<AdminCreateUserDTO> {

    private final ValidationConfig validationConfig;

    public AdminUserRegistrationRequestValidationManager(ValidationConfig validationConfig) {
        this.validationConfig = validationConfig;
    }

    @Override
    public void validate(AdminCreateUserDTO value) {
        validationConfig.getAdminCreateUserValidators().forEach(validator -> validator.validate(value));
    }
}
