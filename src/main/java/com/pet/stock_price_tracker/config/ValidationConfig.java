package com.pet.stock_price_tracker.config;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.dto.admin.AdminCreateUserDTO;
import com.pet.stock_price_tracker.dto.user.login.UserLoginDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.dto.user.restore.UpdateRestorePasswordDTO;
import com.pet.stock_price_tracker.service.validation.config.ValidationConfigurable;
import com.pet.stock_price_tracker.service.validation.validators.BaseValidator;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class ValidationConfig {

    private final ValidationConfigurable configurator;

    private final List<BaseValidator<UserDTO>> userRegistrationValidators;
    private final List<BaseValidator<UserLoginDTO>> userLoginValidators;
    private final List<BaseValidator<TickerRequestDTO>> tickerRequestValidators;
    private final List<BaseValidator<UpdateRestorePasswordDTO>> updateRestorePasswordValidators;
    private final List<BaseValidator<AdminCreateUserDTO>> adminCreateUserValidators;

    public ValidationConfig(
            ValidationConfigurable configurator,
            List<BaseValidator<UserDTO>> userRegistrationValidators,
            List<BaseValidator<UserLoginDTO>> userLoginValidators,
            List<BaseValidator<TickerRequestDTO>> tickerRequestValidators,
            List<BaseValidator<UpdateRestorePasswordDTO>> updateRestorePasswordValidators,
            List<BaseValidator<AdminCreateUserDTO>> adminCreateUserValidators
    ) {
        this.configurator = configurator;

        this.userRegistrationValidators = userRegistrationValidators;
        this.userLoginValidators = userLoginValidators;
        this.tickerRequestValidators = tickerRequestValidators;
        this.updateRestorePasswordValidators = updateRestorePasswordValidators;
        this.adminCreateUserValidators = adminCreateUserValidators;
    }

    @PostConstruct
    public List<BaseValidator<UserDTO>> configureUserValidators() {
        return configurator.config(userRegistrationValidators);
    }

    @PostConstruct
    public List<BaseValidator<UserLoginDTO>> configureUserLoginValidators() {
        return configurator.config(userLoginValidators);
    }

    @PostConstruct
    public List<BaseValidator<TickerRequestDTO>> configureTickerRequestValidators() {
        return configurator.config(tickerRequestValidators);
    }

    @PostConstruct
    public List<BaseValidator<UpdateRestorePasswordDTO>> configurePasswordRestoreValidators() {
        return configurator.config(updateRestorePasswordValidators);
    }

    @PostConstruct
    public List<BaseValidator<AdminCreateUserDTO>> configureAdminCreateUserValidators() {
        return configurator.config(adminCreateUserValidators);
    }
}
