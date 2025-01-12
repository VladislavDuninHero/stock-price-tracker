package com.pet.stock_price_tracker.config;

import com.pet.stock_price_tracker.dto.user.login.UserLoginDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.service.validation.config.ValidationConfigurable;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class ValidationConfig {

    private final ValidationConfigurable configurator;

    private final List<BaseValidator<UserDTO>> userRegistrationValidators;
    private final List<BaseValidator<UserLoginDTO>> userLoginValidators;

    public ValidationConfig(
            ValidationConfigurable configurator,
            List<BaseValidator<UserDTO>> userRegistrationValidators,
            List<BaseValidator<UserLoginDTO>> userLoginValidators
    ) {
        this.configurator = configurator;

        this.userRegistrationValidators = userRegistrationValidators;
        this.userLoginValidators = userLoginValidators;
    }

    @Bean
    public List<BaseValidator<UserDTO>> configurateUserValidators() {
        for (int i = 1; i < userRegistrationValidators.size(); i++) {
            userRegistrationValidators.get(i - 1).setNext(userRegistrationValidators.get(i));
        }

        return userRegistrationValidators;
    }

    @Bean
    public List<BaseValidator<UserLoginDTO>> configurateUserLoginValidators() {
        for (int i = 1; i < userLoginValidators.size(); i++) {
            userLoginValidators.get(i - 1).setNext(userLoginValidators.get(i));
        }

        return userLoginValidators;
    }
}
