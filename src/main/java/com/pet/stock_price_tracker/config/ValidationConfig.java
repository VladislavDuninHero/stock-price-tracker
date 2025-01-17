package com.pet.stock_price_tracker.config;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
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
    private final List<BaseValidator<TickerRequestDTO>> tickerRequestValidators;

    public ValidationConfig(
            ValidationConfigurable configurator,
            List<BaseValidator<UserDTO>> userRegistrationValidators,
            List<BaseValidator<UserLoginDTO>> userLoginValidators,
            List<BaseValidator<TickerRequestDTO>> tickerRequestValidators
    ) {
        this.configurator = configurator;

        this.userRegistrationValidators = userRegistrationValidators;
        this.userLoginValidators = userLoginValidators;
        this.tickerRequestValidators = tickerRequestValidators;
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

    @Bean
    public List<BaseValidator<TickerRequestDTO>> configurateTickerRequestValidators() {
        for (int i = 1; i < tickerRequestValidators.size(); i++) {
            tickerRequestValidators.get(i - 1).setNext(tickerRequestValidators.get(i));
        }

        return tickerRequestValidators;
    }
}
